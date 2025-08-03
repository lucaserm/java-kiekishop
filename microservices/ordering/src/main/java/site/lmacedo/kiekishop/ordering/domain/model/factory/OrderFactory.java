package site.lmacedo.kiekishop.ordering.domain.model.factory;

import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Billing;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Product;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Shipping;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

import java.util.Objects;

public class OrderFactory {

    private OrderFactory() {}

    public static Order filled(
            CustomerId customerId,
            Shipping shipping,
            Billing billing,
            PaymentMethod paymentMethod,
            Product product,
            Quantity productQuantity
    ) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(shipping);
        Objects.requireNonNull(billing);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(product);
        Objects.requireNonNull(productQuantity);

        Order order = Order.draft(customerId);

        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);

        order.addItem(product, productQuantity);

        return order;
    }
}
