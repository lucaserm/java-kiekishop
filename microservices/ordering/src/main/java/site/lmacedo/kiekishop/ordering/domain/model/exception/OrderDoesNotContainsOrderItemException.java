package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderItemId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAINS_ITEM;

public class OrderDoesNotContainsOrderItemException extends DomainException {
    public OrderDoesNotContainsOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAINS_ITEM, id, orderItemId));
    }
}
