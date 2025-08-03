package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCanNotBeChangedException extends DomainException {
    public OrderStatusCanNotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
