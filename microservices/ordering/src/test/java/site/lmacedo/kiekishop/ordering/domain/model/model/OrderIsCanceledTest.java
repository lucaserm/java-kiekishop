package site.lmacedo.kiekishop.ordering.domain.model.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

class OrderIsCanceledTest {

    @Test
    void givenCanceledOrder_whenIsCanceled_shouldReturnTrue() {
        Order order = Order.draft(new CustomerId());
        Assertions.assertThat(order.isCanceled()).isFalse();
        order.cancel();
        Assertions.assertThat(order.isCanceled()).isTrue();
    }

    @Test
    void givenNonCanceledOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = Order.draft(new CustomerId());
        order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));

        Assertions.assertThat(order.isCanceled()).isFalse();
    }

    @Test
    void givenOrderInAnyOtherStatus_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Assertions.assertThat(order.isCanceled()).isFalse();
    }
}
