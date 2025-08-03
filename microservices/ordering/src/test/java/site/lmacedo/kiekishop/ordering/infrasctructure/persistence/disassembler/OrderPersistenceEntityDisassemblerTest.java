package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;

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