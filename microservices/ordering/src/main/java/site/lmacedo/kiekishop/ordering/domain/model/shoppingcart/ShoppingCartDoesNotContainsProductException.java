package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_PRODUCT;

public class ShoppingCartDoesNotContainsProductException extends DomainException {
    public ShoppingCartDoesNotContainsProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAINS_PRODUCT, id, productId));
    }
}
