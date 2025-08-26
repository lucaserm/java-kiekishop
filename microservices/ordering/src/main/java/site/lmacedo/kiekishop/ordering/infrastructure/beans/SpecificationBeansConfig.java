package site.lmacedo.kiekishop.ordering.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.lmacedo.kiekishop.ordering.domain.model.customer.LoyaltyPoints;
import site.lmacedo.kiekishop.ordering.domain.model.order.CustomerHaveFreeShippingSpecification;
import site.lmacedo.kiekishop.ordering.domain.model.order.Orders;

@Configuration
public class SpecificationBeansConfig {

    @Bean
    public CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification(Orders orders) {
        return new CustomerHaveFreeShippingSpecification(
                orders,
                new LoyaltyPoints(200),
                2L,
                new LoyaltyPoints(2000)
        );
    }

}