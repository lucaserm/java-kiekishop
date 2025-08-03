package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;

class OrderPersistenceEntityAssemblerTest {

    private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

    @Test
    void shouldConvertToDomain() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistenceEntity = assembler.fromDomain(order);
        Assertions.assertThat(orderPersistenceEntity).satisfies(
                s -> {
                    Assertions.assertThat(s.getId()).isEqualTo(order.id().value().toLong());
                    Assertions.assertThat(s.getCustomerId()).isEqualTo(order.customerId().value());
                    Assertions.assertThat(s.getTotalAmount()).isEqualByComparingTo(order.totalAmount().value());
                    Assertions.assertThat(s.getTotalItems()).isEqualTo(order.totalItems().value());
                    Assertions.assertThat(s.getStatus()).isEqualTo(order.status().toString());
                    Assertions.assertThat(s.getPaymentMethod()).isEqualTo(order.paymentMethod().toString());
                    Assertions.assertThat(s.getPlacedAt()).isEqualTo(order.placedAt());
                    Assertions.assertThat(s.getPaidAt()).isEqualTo(order.paidAt());
                    Assertions.assertThat(s.getCanceledAt()).isEqualTo(order.canceledAt());
                    Assertions.assertThat(s.getReadyAt()).isEqualTo(order.readyAt());
                }
        );
    }

}