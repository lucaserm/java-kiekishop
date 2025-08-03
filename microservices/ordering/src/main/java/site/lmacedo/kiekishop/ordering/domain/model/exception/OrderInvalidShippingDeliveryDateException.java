package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_PAST;

public class OrderInvalidShippingDeliveryDateException extends DomainException {

    public OrderInvalidShippingDeliveryDateException(OrderId id) {
        super(String.format(ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_PAST, id));
    }
}
