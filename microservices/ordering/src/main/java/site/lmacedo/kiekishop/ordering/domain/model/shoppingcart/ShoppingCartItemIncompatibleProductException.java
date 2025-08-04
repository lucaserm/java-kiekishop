package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
