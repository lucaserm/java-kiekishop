package site.lmacedo.kiekishop.ordering.infrastructure.persistence.disassembler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.order.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderId;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.order.OrderPersistenceEntity;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.order.OrderPersistenceEntityDisassembler;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void shouldConvertFromPersistence() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomain(entity);
        Assertions.assertThat(domainEntity).satisfies(
                s -> {
                    Assertions.assertThat(s.id()).isEqualTo(new OrderId(entity.getId()));
                    Assertions.assertThat(s.customerId()).isEqualTo(new CustomerId(entity.getCustomerId()));
                    Assertions.assertThat(s.totalAmount()).isEqualByComparingTo(new Money(entity.getTotalAmount()));
                    Assertions.assertThat(s.totalItems()).isEqualTo(new Quantity(entity.getTotalItems()));
                    Assertions.assertThat(s.status()).isEqualTo(OrderStatus.valueOf(entity.getStatus()));
                    Assertions.assertThat(s.paymentMethod()).isEqualTo(PaymentMethod.valueOf(entity.getPaymentMethod()));
                    Assertions.assertThat(s.placedAt()).isEqualTo(entity.getPlacedAt());
                    Assertions.assertThat(s.paidAt()).isEqualTo(entity.getPaidAt());
                    Assertions.assertThat(s.canceledAt()).isEqualTo(entity.getCanceledAt());
                    Assertions.assertThat(s.readyAt()).isEqualTo(entity.getReadyAt());
                }
        );

    }
}