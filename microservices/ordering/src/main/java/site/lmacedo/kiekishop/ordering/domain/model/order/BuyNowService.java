package site.lmacedo.kiekishop.ordering.domain.model.order;

import site.lmacedo.kiekishop.ordering.domain.model.DomainService;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.product.Product;

@DomainService
public class BuyNowService {

    public Order buyNow(Product product,
                        CustomerId customerId,
                        Billing billing,
                        Shipping shipping,
                        Quantity quantity,
                        PaymentMethod paymentMethod) {

        product.checkOutOfStock();

        Order order = Order.draft(customerId);
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);
        order.place();

        return order;
    }

}