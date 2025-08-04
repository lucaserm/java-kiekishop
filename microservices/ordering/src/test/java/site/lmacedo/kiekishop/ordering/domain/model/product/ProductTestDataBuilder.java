package site.lmacedo.kiekishop.ordering.domain.model.product;

import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;

public class ProductTestDataBuilder {
    public static final ProductId DEFAULT_PRODUCT_ID = new ProductId();

    private ProductTestDataBuilder() {
    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Notebook X11"))
                .price(new Money("3000"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .price(new Money("5000"))
                .name(new ProductName("Desktop FX9000"))
                .inStock(false);
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product.builder()
                .id(new ProductId())
                .name(new ProductName("8GB RAM"))
                .price(new Money("200"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductAltMouse() {
        return Product.builder()
                .id(new ProductId())
                .name(new ProductName("Mouse"))
                .price(new Money("100"))
                .inStock(true);
    }
}
