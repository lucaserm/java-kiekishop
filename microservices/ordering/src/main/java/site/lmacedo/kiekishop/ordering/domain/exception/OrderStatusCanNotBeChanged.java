package site.lmacedo.kiekishop.ordering.domain.exception;

import site.lmacedo.kiekishop.ordering.domain.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.OrderId;

import static site.lmacedo.kiekishop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCanNotBeChanged extends DomainException {
    public OrderStatusCanNotBeChanged(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
