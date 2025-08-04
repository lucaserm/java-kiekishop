package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.model.CustomerTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.repository.Orders;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.OrderPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.config.SpringDataAuditingConfig;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository.OrderPersistenceEntityRepository;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class OrdersPersistenceProviderIT {
    private OrdersPersistenceProvider provider;
    private CustomersPersistenceProvider customersProvider;
    private OrderPersistenceEntityRepository repository;
    private Orders orders;

    @Autowired
    public OrdersPersistenceProviderIT(OrdersPersistenceProvider provider, CustomersPersistenceProvider customersProvider, OrderPersistenceEntityRepository repository, Orders orders) {
        this.provider = provider;
        this.customersProvider = customersProvider;
        this.repository = repository;
        this.orders = orders;
    }

    @BeforeEach
    void setup() {
        if(!customersProvider.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customersProvider.add(CustomerTestDataBuilder.existingCustomer().build());
        }
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

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddFindAndNotFailWhenNoTransactional() {
        Order order = OrderTestDataBuilder.anOrder().build();
        provider.add(order);

        Assertions.assertThatNoException().isThrownBy(() -> provider.ofId(order.id()).orElseThrow());
    }
}