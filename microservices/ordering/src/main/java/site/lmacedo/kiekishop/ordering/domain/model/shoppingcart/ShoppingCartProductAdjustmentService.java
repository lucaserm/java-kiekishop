package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;

public interface ShoppingCartProductAdjustmentService {
    void adjustPrice(ProductId productId, Money updatedPrice);
    void changeAvailability(ProductId productId, boolean available);
}