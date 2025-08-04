package site.lmacedo.kiekishop.ordering.domain.model.product;

import java.util.Optional;

public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}