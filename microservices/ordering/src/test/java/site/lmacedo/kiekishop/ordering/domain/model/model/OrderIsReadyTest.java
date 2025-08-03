package site.lmacedo.kiekishop.ordering.domain.model.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderIsReadyTest {

    @Test
    void givenOrderWithStatusReady_whenIsReady_shouldReturnTrue() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();
        Assertions.assertThat(order.isReady()).isTrue();
    }

    @Test
    void givenOrderWithStatusPaid_whenIsReady_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Assertions.assertThat(order.isReady()).isFalse();
    }

    @Test
    void givenOrderWithStatusDraft_whenIsReady_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();
        Assertions.assertThat(order.isReady()).isFalse();
    }

    @Test
    void givenOrderWithStatusCanceled_whenIsReady_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();
        Assertions.assertThat(order.isReady()).isFalse();
    }
}