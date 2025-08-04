package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_ITEM;

public class ShoppingCartDoesNotContainsItemException extends DomainException {
    public ShoppingCartDoesNotContainsItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_ITEM, id, shoppingCartItemId));
    }
}
