package site.lmacedo.kiekishop.ordering.domain.model.exception;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ProductId;

import static site.lmacedo.kiekishop.ordering.domain.model.exception.ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK;

public class ProductOutOfStockException extends DomainException {
    public ProductOutOfStockException(ProductId id) {
        super(String.format(ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }
}
