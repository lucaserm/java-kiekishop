package site.lmacedo.kiekishop.ordering.domain.model.customer;

import lombok.RequiredArgsConstructor;
import site.lmacedo.kiekishop.ordering.domain.model.DomainService;
import site.lmacedo.kiekishop.ordering.domain.model.commons.*;

@DomainService
@RequiredArgsConstructor
public class CustomerRegistrationService {

    private final Customers customers;

    public Customer register(
            FullName fullName, BirthDate birthDate, Email email,
            Phone phone, Document document, Boolean promotionNotificationsAllowed,
            Address address
    ) {

        Customer customer = Customer.brandNew()
                .fullName(fullName)
                .birthDate(birthDate)
                .email(email)
                .phone(phone)
                .document(document)
                .promotionNotificationsAllowed(promotionNotificationsAllowed)
                .address(address)
                .build();

        verifyEmailUniqueness(customer.email(), customer.id());

        return customer;
    }

    public void changeEmail(Customer customer, Email newEmail) {
        verifyEmailUniqueness(newEmail, customer.id());
        customer.changeEmail(newEmail);
    }

    private void verifyEmailUniqueness(Email email, CustomerId customerId) {
        if (!customers.isEmailUnique(email, customerId)) {
            throw new CustomerEmailIsInUseException();
        }
    }

}