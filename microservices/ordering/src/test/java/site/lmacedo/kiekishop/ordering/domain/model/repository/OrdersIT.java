package site.lmacedo.kiekishop.ordering.domain.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site.lmacedo.kiekishop.ordering.domain.model.model.CustomerTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.OrderPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.provider.CustomersPersistenceProvider;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.provider.OrdersPersistenceProvider;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
class OrdersIT {

    private Orders orders;
    private Customers customers;

    @Autowired
    public OrdersIT(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @BeforeEach
    void setup() {
        if(!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    void shouldPersistAndFind() {
        Order originalOrder = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = originalOrder.id();
        orders.add(originalOrder);

        Optional<Order> possibleOrder = orders.ofId(orderId);

        Assertions.assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        Assertions.assertThat(savedOrder).satisfies(
                s -> {
                    Assertions.assertThat(s.id()).isEqualTo(orderId);
                    Assertions.assertThat(s.customerId()).isEqualTo(originalOrder.customerId());
                    Assertions.assertThat(s.totalAmount()).isEqualByComparingTo(originalOrder.totalAmount());
                    Assertions.assertThat(s.totalItems()).isEqualTo(originalOrder.totalItems());
                    Assertions.assertThat(s.placedAt()).isEqualTo(originalOrder.placedAt());
                    Assertions.assertThat(s.paidAt()).isEqualTo(originalOrder.paidAt());
                    Assertions.assertThat(s.canceledAt()).isEqualTo(originalOrder.canceledAt());
                    Assertions.assertThat(s.readyAt()).isEqualTo(originalOrder.readyAt());
                    Assertions.assertThat(s.status()).isEqualTo(originalOrder.status());
                    Assertions.assertThat(s.paymentMethod()).isEqualTo(originalOrder.paymentMethod());
                }
        );
    }

    @Test
    void shouldUpdateExistingOrder() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();
        order.markAsPaid();

        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(order.isPaid()).isTrue();
    }

    @Test
    void shouldCountExistingOrders() {
        Assertions.assertThat(orders.count()).isZero();
        Order order1 = OrderTestDataBuilder.anOrder().build();
        Order order2 = OrderTestDataBuilder.anOrder().build();
        orders.add(order1);
        orders.add(order2);
        Assertions.assertThat(orders.count()).isEqualTo(2L);
    }

    @Test
    void shouldReturnIfOrderExists() {
        Order order = OrderTestDataBuilder.anOrder().build();
        orders.add(order);
        Assertions.assertThat(orders.exists(order.id())).isTrue();
        Assertions.assertThat(orders.exists(new OrderId())).isFalse();
    }

    @Test
    void shouldListExistingOrdersByYear() {
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        List<Order> listedOrders = orders.placedByCustomerInYear(customerId, Year.now());

        Assertions.assertThat(listedOrders).hasSize(2);

        listedOrders = orders.placedByCustomerInYear(customerId, Year.now().minusYears(1));

        Assertions.assertThat(listedOrders).isEmpty();

        listedOrders = orders.placedByCustomerInYear(new CustomerId(), Year.now().minusYears(1));

        Assertions.assertThat(listedOrders).isEmpty();
    }

    @Test
    void shouldReturnTotalSoldByCustomer() {
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        orders.add(order1);
        orders.add(order2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        Money expectedAmount = order1.totalAmount().add(order2.totalAmount());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Assertions.assertThat(orders.totalSoldForCustomer(customerId)).isEqualTo(expectedAmount);
        Assertions.assertThat(orders.totalSoldForCustomer(new CustomerId())).isEqualTo(Money.ZERO);
    }

    @Test
    void shouldReturnSalesQuantityByCustomer() {
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        orders.add(order1);
        orders.add(order2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Assertions.assertThat(orders.salesQuantityByCustomerInYear(customerId, Year.now())).isEqualTo(2L);
        Assertions.assertThat(orders.salesQuantityByCustomerInYear(customerId, Year.now().minusYears(1))).isZero();
        Assertions.assertThat(orders.salesQuantityByCustomerInYear(new CustomerId(), Year.now())).isZero();
    }
}