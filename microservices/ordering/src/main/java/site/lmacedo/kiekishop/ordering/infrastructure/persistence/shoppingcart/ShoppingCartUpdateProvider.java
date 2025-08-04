package site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartProductAdjustmentService;

@Component
@RequiredArgsConstructor
public class ShoppingCartUpdateProvider implements ShoppingCartProductAdjustmentService {

    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;

    @Override
    @Transactional
    public void adjustPrice(ProductId productId, Money updatedPrice) {
        shoppingCartPersistenceEntityRepository.updateItemPrice(productId.value(), updatedPrice.value());
        shoppingCartPersistenceEntityRepository.recalculateTotalsForCartsWithProduct(productId.value());
    }

    @Override
    @Transactional
    public void changeAvailability(ProductId productId, boolean available) {
        shoppingCartPersistenceEntityRepository.updateItemAvailability(productId.value(), available);
    }
}