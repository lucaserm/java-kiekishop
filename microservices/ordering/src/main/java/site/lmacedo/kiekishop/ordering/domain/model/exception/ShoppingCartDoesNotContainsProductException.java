package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ProductId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ShoppingCartId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_PRODUCT;

public class ShoppingCartDoesNotContainsProductException extends DomainException {
    public ShoppingCartDoesNotContainsProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_PRODUCT, id, productId));
    }
}
