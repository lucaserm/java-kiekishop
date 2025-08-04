package site.lmacedo.kiekishop.ordering.infrastructure.product.client.fake;


import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.product.Product;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductCatalogService;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductName;

import java.util.Optional;

@Component
public class ProductCatalogServiceFakeImpl implements ProductCatalogService {
    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder().id(productId)
                .inStock(true)
                .name(new ProductName("Notebook"))
                .price(new Money("3000"))
                .build();
        return Optional.of(product);
    }
}