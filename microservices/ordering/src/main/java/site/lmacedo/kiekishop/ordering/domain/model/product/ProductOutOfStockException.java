package site.lmacedo.kiekishop.ordering.domain.model.product;

import site.lmacedo.kiekishop.ordering.domain.model.DomainException;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK;

public class ProductOutOfStockException extends DomainException {
    public ProductOutOfStockException(ProductId id) {
        super(String.format(ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }
}
