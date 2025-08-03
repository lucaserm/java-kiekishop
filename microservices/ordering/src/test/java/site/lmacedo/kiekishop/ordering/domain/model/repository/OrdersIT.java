package site.lmacedo.kiekishop.ordering.domain.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.OrderPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.provider.OrdersPersistenceProvider;

import java.util.Optional;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class
})
class OrdersIT {

    private Orders orders;

    @Autowired
    public OrdersIT(Orders orders) {
        this.orders = orders;
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
}