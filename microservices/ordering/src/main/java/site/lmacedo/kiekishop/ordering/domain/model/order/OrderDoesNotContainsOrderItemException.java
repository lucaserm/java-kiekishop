package site.lmacedo.kiekishop.ordering.domain.model.order;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAINS_ITEM;

public class OrderDoesNotContainsOrderItemException extends DomainException {
    public OrderDoesNotContainsOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAINS_ITEM, id, orderItemId));
    }
}
