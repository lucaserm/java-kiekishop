package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED;

public class OrderCannotBeEditedException extends DomainException {
    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }
}
