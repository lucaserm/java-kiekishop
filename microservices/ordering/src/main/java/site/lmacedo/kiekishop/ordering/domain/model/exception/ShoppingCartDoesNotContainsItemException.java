package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ShoppingCartId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ShoppingCartItemId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_ITEM;

public class ShoppingCartDoesNotContainsItemException extends DomainException {
    public ShoppingCartDoesNotContainsItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_ITEM, id, shoppingCartItemId));
    }
}
