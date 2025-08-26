package site.lmacedo.kiekishop.ordering.application.checkout;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.application.commons.AddressData;
import site.lmacedo.kiekishop.ordering.application.order.query.BillingData;
import site.lmacedo.kiekishop.ordering.domain.model.commons.*;
import site.lmacedo.kiekishop.ordering.domain.model.order.Billing;

@Component
class BillingInputDisassembler {
    public Billing toDomainModel(BillingData billingData) {
        AddressData address = billingData.getAddress();
        return Billing.builder()
                .fullName(new FullName(billingData.getFirstName(), billingData.getLastName()))
                .document(new Document(billingData.getDocument()))
                .phone(new Phone(billingData.getPhone()))
                .email(new Email(billingData.getEmail()))
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}