package site.lmacedo.kiekishop.ordering.infrastructure.notification.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.lmacedo.kiekishop.ordering.application.customer.notification.CustomerNotificationApplicationService;

@Service
@Slf4j
public class CustomerNotificationApplicationServiceFakeImpl implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}", input.email());
    }
}
