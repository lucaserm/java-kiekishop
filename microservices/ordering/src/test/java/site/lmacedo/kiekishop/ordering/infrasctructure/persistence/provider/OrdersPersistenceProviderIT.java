package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.repository.Orders;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.OrderPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.config.SpringDataAuditingConfig;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository.OrderPersistenceEntityRepository;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class OrdersPersistenceProviderIT {
    private OrdersPersistenceProvider provider;
    private OrderPersistenceEntityRepository repository;
    @Autowired
    private Orders orders;

    @Autowired
    public OrdersPersistenceProviderIT(OrdersPersistenceProvider provider, OrderPersistenceEntityRepository repository) {
        this.provider = provider;
        this.repository = repository;
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        long orderId = order.id().value().toLong();
        provider.add(order);

        var entity = repository.findById(orderId).orElseThrow();

        Assertions.assertThat(entity.getStatus()).isEqualTo(OrderStatus.PLACED.name());

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();

        order = provider.ofId(order.id()).orElseThrow();

        order.markAsPaid();
        provider.add(order);

        entity = repository.findById(orderId).orElseThrow();

        Assertions.assertThat(entity.getStatus()).isEqualTo(OrderStatus.PAID.name());

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        provider.add(order);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        Order orderT2 = orders.ofId(order.id()).orElseThrow();

        orderT1.markAsPaid();
        orders.add(orderT1);

        orderT2.cancel();

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> orders.add(orderT2));

        Order savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder.canceledAt()).isNull();
        Assertions.assertThat(savedOrder.paidAt()).isNotNull();
    }
}