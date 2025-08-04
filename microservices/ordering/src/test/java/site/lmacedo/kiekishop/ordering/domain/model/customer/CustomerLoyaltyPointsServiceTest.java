package site.lmacedo.kiekishop.ordering.domain.model.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.product.ProductTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.product.Product;

class CustomerLoyaltyPointsServiceTest {

    CustomerLoyaltyPointsService customerLoyaltyPointsService = new CustomerLoyaltyPointsService();

    @Test
    void givenValidCustomerAndOrder_WhenAddingPoints_ShouldAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(40));
    }

    @Test
    void givenValidCustomerAndOrderWithLowTotalAmount_WhenAddingPoints_ShouldNotAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();

        Order order = OrderTestDataBuilder.anOrder().withItems(false).status(OrderStatus.DRAFT).build();
        order.addItem(product, new Quantity(1));
        order.place();
        order.markAsPaid();
        order.markAsReady();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(10));
    }

}