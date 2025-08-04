package site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customers;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Email;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomersPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository repository;
    private final CustomerPersistenceEntityAssembler assembler;
    private final CustomerPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
        Optional<CustomerPersistenceEntity> possibleEntity = repository.findById(customerId.value());
        return possibleEntity.map(disassembler::toDomain);
    }

    @Override
    public boolean exists(CustomerId customerId) {
        return repository.existsById(customerId.value());
    }

    @Override
    @Transactional
    public void add(Customer aggregateRoot) {
        UUID customerId = aggregateRoot.id().value();
        repository.findById(customerId).ifPresentOrElse(
                existingEntity -> update(aggregateRoot, existingEntity),
                () -> insert(aggregateRoot)
        );
    }

    private void update(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceEntity = repository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void insert(Customer aggregateRoot) {
        CustomerPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        repository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @SneakyThrows
    @SuppressWarnings("squid:S3011")
    private void updateVersion(Customer aggregateRoot, CustomerPersistenceEntity existingEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, existingEntity.getVersion());
        version.setAccessible(false);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Customer> ofEmail(Email email) {
        return repository.findByEmail(email.value()).map(disassembler::toDomain);
    }

    @Override
    public boolean isEmailUnique(Email email, CustomerId exceptCustomerId) {
        return !repository.existsByEmailAndIdNot(email.value(), exceptCustomerId.value());
    }
}
