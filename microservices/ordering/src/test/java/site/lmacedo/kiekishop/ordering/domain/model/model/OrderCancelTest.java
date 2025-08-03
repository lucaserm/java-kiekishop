package site.lmacedo.kiekishop.ordering.domain.model.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.exception.OrderStatusCanNotBeChangedException;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

class OrderCancelTest {

    @Test
    void givenEmptyOrder_whenCancel_shouldAllow() {
        Order order = Order.draft(new CustomerId());

        order.cancel();

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                i -> Assertions.assertThat(i.isCanceled()).isTrue(),
                i -> Assertions.assertThat(i.canceledAt()).isNotNull()
        );
    }

    @Test
    void givenFilledOrder_whenCancel_shouldAllow() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        order.cancel();

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                i -> Assertions.assertThat(i.isCanceled()).isTrue(),
                i -> Assertions.assertThat(i.canceledAt()).isNotNull()
        );
    }

    @Test
    void givenCanceledOrder_whenCancelAgain_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCanNotBeChangedException.class)
                .isThrownBy(order::cancel);

        Assertions.assertWith(order,
                i -> Assertions.assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                i -> Assertions.assertThat(i.isCanceled()).isTrue(),
                i -> Assertions.assertThat(i.canceledAt()).isNotNull()
        );
    }

}