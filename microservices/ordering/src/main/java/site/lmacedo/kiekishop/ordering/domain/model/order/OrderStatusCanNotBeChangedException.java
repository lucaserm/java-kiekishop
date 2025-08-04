package site.lmacedo.kiekishop.ordering.domain.model.order;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCanNotBeChangedException extends DomainException {
    public OrderStatusCanNotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
    }
}
