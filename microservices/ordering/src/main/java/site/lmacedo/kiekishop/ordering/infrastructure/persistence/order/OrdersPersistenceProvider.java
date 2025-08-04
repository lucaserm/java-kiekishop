package site.lmacedo.kiekishop.ordering.infrastructure.persistence.order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.Orders;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderId;

import java.lang.reflect.Field;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository repository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleEntity = repository.findById(orderId.value().toLong());
        return possibleEntity.map(disassembler::toDomain);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return repository.existsById(orderId.value().toLong());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();
        repository.findById(orderId).ifPresentOrElse(
                existingEntity -> update(aggregateRoot, existingEntity),
                () -> insert(aggregateRoot)
        );
    }

    @Override
    public List<Order> placedByCustomerInYear(CustomerId customerId, Year year) {
        List<OrderPersistenceEntity> entities = repository.placedByCustomerInYear(customerId.value(), year.getValue());
        return entities.stream().map(disassembler::toDomain).toList();
    }

    @Override
    public long salesQuantityByCustomerInYear(CustomerId customerId, Year year) {
        return repository.salesQuantityByCustomerInYear(customerId.value(), year.getValue());
    }

    @Override
    public Money totalSoldForCustomer(CustomerId customerId) {
        return new Money(repository.totalSoldForCustomer(customerId.value()));
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceEntity = repository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        repository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @SneakyThrows
    @SuppressWarnings("squid:S3011")
    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity existingEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, existingEntity.getVersion());
        version.setAccessible(false);
    }
}
