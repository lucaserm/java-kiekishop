package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.*;

public class OrderCannotBePlacedException extends DomainException {
    private OrderCannotBePlacedException(String message) {
        super(message);
    }

    public static OrderCannotBePlacedException noItems(OrderId id) {
        return new OrderCannotBePlacedException(
                String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, id)
        );
    }

    public static OrderCannotBePlacedException noShippingInfo(OrderId id) {
        return new OrderCannotBePlacedException(
                String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, id)
        );
    }

    public static OrderCannotBePlacedException noBillingInfo(OrderId id) {
        return new OrderCannotBePlacedException(
                String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, id)
        );
    }

    public static OrderCannotBePlacedException noPaymentMethod(OrderId id) {
        return new OrderCannotBePlacedException(
                String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, id)
        );
    }
}
