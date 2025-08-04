package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.RemoveCapableRepository;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {
    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}
