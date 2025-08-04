package site.lmacedo.kiekishop.ordering.domain.model.product;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;

import java.util.Objects;

@Builder
public record Product(
        ProductId id,
        ProductName name,
        Money price,
        Boolean inStock
) {
    public Product {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(inStock);
    }

    public void checkOutOfStock() {
        if(isOutOfStock()) {
            throw new ProductOutOfStockException(this.id());
        }
    }

    private boolean isOutOfStock() {
        return !inStock();
    }
}
