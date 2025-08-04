package site.lmacedo.kiekishop.ordering.application.checkout;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customers;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderId;
import site.lmacedo.kiekishop.ordering.domain.model.order.Orders;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.ShippingCostService;
import site.lmacedo.kiekishop.ordering.domain.model.product.Product;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductCatalogService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Transactional
class BuyNowApplicationServiceIT {

    @Autowired
    private BuyNowApplicationService buyNowApplicationService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @MockitoBean
    private ShippingCostService shippingCostService;

    @BeforeEach
    void setup() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    void shouldBuyNow() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Mockito.when(productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

        Mockito.when(shippingCostService.calculate(Mockito.any(ShippingCostService.CalculationRequest.class)))
                .thenReturn(new ShippingCostService.CalculationResult(
                        new Money("10.00"),
                        LocalDate.now().plusDays(3)
                ));

        BuyNowInput input = BuyNowInputTestDataBuilder.aBuyNowInput().build();

        String orderId = buyNowApplicationService.buyNow(input);

        Assertions.assertThat(orderId).isNotBlank();
        Assertions.assertThat(orders.exists(new OrderId(orderId))).isTrue();
    }

}