package site.lmacedo.kiekishop.ordering.domain.model.factory;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.model.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.model.ProductTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Billing;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Product;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Shipping;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

class OrderFactoryTest {

    @Test
    void shouldGenerateFilledOrderThatCanBePlaced() {
        CustomerId customerId = new CustomerId();
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();
        PaymentMethod gatewayBalance = PaymentMethod.GATEWAY_BALANCE;
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity productQuantity = new Quantity(1);
        Order order = OrderFactory.filled(
                customerId,
                shipping,
                billing,
                gatewayBalance,
                product,
                productQuantity
        );

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.customerId()).isEqualTo(customerId),
                    o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
                o -> Assertions.assertThat(o.billing()).isEqualTo(billing),
                o -> Assertions.assertThat(o.paymentMethod()).isEqualTo(gatewayBalance),
                o -> Assertions.assertThat(o.items()).isNotEmpty(),
                o -> Assertions.assertThat(o.isDraft()).isTrue()
                );

        order.place();

        Assertions.assertThat(order.isPlaced()).isTrue();
    }
}