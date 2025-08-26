package site.lmacedo.kiekishop.ordering.infrastructure.listener.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import site.lmacedo.kiekishop.ordering.application.customer.notification.CustomerNotificationApplicationService;
import site.lmacedo.kiekishop.ordering.application.customer.notification.CustomerNotificationApplicationService.NotifyNewRegistrationInput;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerArchivedEvent;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerRegisteredEvent;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderReadyEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerNotificationApplicationService customerNotificationApplicationService;
    private final CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @EventListener
    public void listen(CustomerRegisteredEvent event) {
        log.info("CustomerRegisteredEvent listen 1");
        NotifyNewRegistrationInput input = new NotifyNewRegistrationInput(
                event.customerId().value(),
                event.fullName().firstName(),
                event.email().value()
        );
        customerNotificationApplicationService.notifyNewRegistration(input);
    }

    @EventListener
    public void listen(CustomerArchivedEvent event) {
        log.info("CustomerArchivedEvent listen 1");
    }

    @EventListener
    public void listen(OrderReadyEvent event) {
        customerLoyaltyPointsApplicationService.addLoyaltyPoints(event.customerId().value(),
                event.orderId().toString());
    }

}