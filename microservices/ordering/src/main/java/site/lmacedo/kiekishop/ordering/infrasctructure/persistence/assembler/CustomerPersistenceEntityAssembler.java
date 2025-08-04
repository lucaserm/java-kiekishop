package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.model.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.BirthDate;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.CustomerPersistenceEntity;

import java.time.LocalDate;

import static site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler.OrderPersistenceEntityAssembler.toAddressEmbeddable;

@Component
public class CustomerPersistenceEntityAssembler {
    public CustomerPersistenceEntity fromDomain(Customer customer) {
        return this.merge(new CustomerPersistenceEntity(), customer);
    }

    public CustomerPersistenceEntity merge(CustomerPersistenceEntity customerPersistenceEntity, Customer customer) {
        customerPersistenceEntity.setId(customer.id().value());
        customerPersistenceEntity.setFirstName(customer.fullName().firstName());
        customerPersistenceEntity.setLastName(customer.fullName().lastName());
        customerPersistenceEntity.setBirthDate(toBirthDate(customer.birthDate()));
        customerPersistenceEntity.setEmail(customer.email().value());
        customerPersistenceEntity.setPhone(customer.phone().value());
        customerPersistenceEntity.setDocument(customer.document().value());
        customerPersistenceEntity.setPromotionNotificationsAllowed(customer.isPromotionNotificationsAllowed());
        customerPersistenceEntity.setArchived(customer.isArchived());
        customerPersistenceEntity.setRegisteredAt(customer.registeredAt());
        customerPersistenceEntity.setArchivedAt(customer.archivedAt());
        customerPersistenceEntity.setAddress(toAddressEmbeddable(customer.address()));
        customerPersistenceEntity.setLoyaltyPoints(customer.loyaltyPoints().value());
        customerPersistenceEntity.setVersion(customer.version());
        return customerPersistenceEntity;
    }

    private LocalDate toBirthDate(BirthDate birthDate) {
        if (birthDate == null) return null;
        return birthDate.value();
    }

}
