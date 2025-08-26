package site.lmacedo.kiekishop.ordering.domain.model.order;

import site.lmacedo.kiekishop.ordering.domain.model.Specification;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.customer.LoyaltyPoints;

public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final CustomerHasOrderedEnoughAtYearSpecification hasOrderedEnoughAtYear;
    private final CustomerHasEnoughLoyaltyPointsSpecification hasEnoughBasicLoyaltyPoints;
    private final CustomerHasEnoughLoyaltyPointsSpecification hasEnoughPremiumLoyaltyPoints;

    public CustomerHaveFreeShippingSpecification(Orders orders,
                                                 LoyaltyPoints basicLoyaltyPoints,
                                                 long salesQuantityForFreeShipping,
                                                 LoyaltyPoints premiumLoyaltyPoints) {
        this.hasOrderedEnoughAtYear = new CustomerHasOrderedEnoughAtYearSpecification(orders,
                salesQuantityForFreeShipping);
        this.hasEnoughBasicLoyaltyPoints = new CustomerHasEnoughLoyaltyPointsSpecification(basicLoyaltyPoints);
        this.hasEnoughPremiumLoyaltyPoints = new CustomerHasEnoughLoyaltyPointsSpecification(premiumLoyaltyPoints);
    }

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return hasEnoughBasicLoyaltyPoints
                .and(hasOrderedEnoughAtYear)
                .or(hasEnoughPremiumLoyaltyPoints)
                .isSatisfiedBy(customer);
    }
}