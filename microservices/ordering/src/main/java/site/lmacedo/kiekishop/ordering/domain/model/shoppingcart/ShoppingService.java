package site.lmacedo.kiekishop.ordering.domain.model.shoppingcart;

import lombok.RequiredArgsConstructor;
import site.lmacedo.kiekishop.ordering.domain.model.DomainService;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerAlreadyHaveShoppingCartException;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerNotFoundException;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customers;

@DomainService
@RequiredArgsConstructor
public class ShoppingService {

    private final ShoppingCarts shoppingCarts;
    private final Customers customers;

    public ShoppingCart startShopping(CustomerId customerId) {
        if (!customers.exists(customerId)) {
            throw new CustomerNotFoundException();
        }

        if (shoppingCarts.ofCustomer(customerId).isPresent()) {
            throw new CustomerAlreadyHaveShoppingCartException();
        }

        return ShoppingCart.startShopping(customerId);
    }

}
