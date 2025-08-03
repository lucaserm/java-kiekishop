package site.lmacedo.kiekishop.ordering.domain.model.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.exception.OrderCannotBeEditedException;
import site.lmacedo.kiekishop.ordering.domain.model.exception.OrderDoesNotContainsOrderItemException;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderItemId;

class OrderRemoveItemTest {

    @Test
    void givenDraftOrder_whenRemoveItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(2)
        );

        OrderItem orderItem1 = order.items().iterator().next();

        order.addItem(
                ProductTestDataBuilder.aProductAltRamMemory().build(),
                new Quantity(3)
        );

        order.removeItem(orderItem1.id());

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("600.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenDraftOrder_whenTryToRemoveNoExistingItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderItemId orderItemId = new OrderItemId();

        Assertions.assertThatExceptionOfType(OrderDoesNotContainsOrderItemException.class)
                .isThrownBy(() -> order.removeItem(orderItemId));

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6110.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenPlacedOrder_whenTryToRemoveItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        OrderItemId orderItemId = new OrderItemId();
        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.removeItem(orderItemId));

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("6110.00")),
                i -> Assertions.assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

}