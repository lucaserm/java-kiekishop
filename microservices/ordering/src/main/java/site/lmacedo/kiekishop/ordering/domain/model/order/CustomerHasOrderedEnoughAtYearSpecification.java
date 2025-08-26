package site.lmacedo.kiekishop.ordering.domain.model.order;

import lombok.RequiredArgsConstructor;
import site.lmacedo.kiekishop.ordering.domain.model.Specification;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customer;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHasOrderedEnoughAtYearSpecification implements Specification<Customer> {

    private final Orders orders;
    private final long expectedOrderCount;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return orders.salesQuantityByCustomerInYear(
                customer.id(),
                Year.now()
        ) >= expectedOrderCount;
    }
}