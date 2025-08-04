package site.lmacedo.kiekishop.ordering.application.checkout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductCatalogService;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductNotFoundException;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCart;
import site.lmacedo.kiekishop.ordering.domain.model.order.CheckoutService;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.OriginAddressService;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.ShippingCostService;
import site.lmacedo.kiekishop.ordering.domain.model.order.Orders;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCarts;
import site.lmacedo.kiekishop.ordering.domain.model.product.Product;
import site.lmacedo.kiekishop.ordering.domain.model.commons.ZipCode;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductId;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartId;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService {

    private final Orders orders;
    private final ShoppingCarts shoppingCarts;
    private final CheckoutService checkoutService;

    private final BillingInputDisassembler billingInputDisassembler;
    private final ShippingInputDisassembler shippingInputDisassembler;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;
    private final ProductCatalogService productCatalogService;

    @Transactional
    public String checkout(CheckoutInput input) {
        Objects.requireNonNull(input);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        ShoppingCartId shoppingCartId = new ShoppingCartId(input.getShoppingCartId());
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Order order = checkoutService.checkout(shoppingCart,
                billingInputDisassembler.toDomainModel(input.getBilling()),
                shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult),
                paymentMethod);

        orders.add(order);
        shoppingCarts.add(shoppingCart);

        return order.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput shipping) {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode(shipping.getAddress().getZipCode());
        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(origin, destination));
    }

    private Product findProduct(ProductId productId) {
        return productCatalogService.ofId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

}