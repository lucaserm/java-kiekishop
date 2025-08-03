package site.lmacedo.kiekishop.ordering.domain.exception;

import site.lmacedo.kiekishop.ordering.domain.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.OrderItemId;

import static site.lmacedo.kiekishop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAINS_ITEM;

public class OrderDoesNotContainsOrderItemException extends DomainException {
    public OrderDoesNotContainsOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAINS_ITEM, id, orderItemId));
    }
}
