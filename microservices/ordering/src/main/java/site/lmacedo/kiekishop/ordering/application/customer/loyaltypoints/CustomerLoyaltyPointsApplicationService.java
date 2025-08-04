package site.lmacedo.kiekishop.ordering.application.customer.loyaltypoints;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.customer.*;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderNotFoundException;
import site.lmacedo.kiekishop.ordering.domain.model.order.Orders;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderId;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService {

    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;
    private final Orders orders;
    private final Customers customers;

    @Transactional
    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {
        CustomerId customerId = new CustomerId(rawCustomerId);
        OrderId orderId = new OrderId(rawOrderId);

        Order order = orders.ofId(orderId)
                .orElseThrow(OrderNotFoundException::new);
        Customer customer = customers.ofId(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customerLoyaltyPointsService.addPoints(customer, order);

        customers.add(customer);
    }

}