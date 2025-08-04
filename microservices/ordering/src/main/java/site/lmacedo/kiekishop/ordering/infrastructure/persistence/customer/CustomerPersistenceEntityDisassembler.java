package site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Document;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Email;
import site.lmacedo.kiekishop.ordering.domain.model.commons.FullName;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Phone;
import site.lmacedo.kiekishop.ordering.domain.model.customer.BirthDate;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.customer.LoyaltyPoints;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;

import java.time.LocalDate;

import static site.lmacedo.kiekishop.ordering.infrastructure.persistence.order.OrderPersistenceEntityDisassembler.toAddressValueObject;

@Component
public class CustomerPersistenceEntityDisassembler {
    public Customer toDomain(CustomerPersistenceEntity persistenceEntity) {
        return Customer.existing()
                .id(new CustomerId(persistenceEntity.getId()))
                .version(persistenceEntity.getVersion())
                .fullName(new FullName(persistenceEntity.getFirstName(), persistenceEntity.getLastName()))
                .birthDate(toBirthDate(persistenceEntity.getBirthDate()))
                .email(new Email(persistenceEntity.getEmail()))
                .phone(new Phone(persistenceEntity.getPhone()))
                .document(new Document(persistenceEntity.getDocument()))
                .promotionNotificationsAllowed(persistenceEntity.getPromotionNotificationsAllowed())
                .archived(persistenceEntity.getArchived())
                .archivedAt(persistenceEntity.getArchivedAt())
                .registeredAt(persistenceEntity.getRegisteredAt())
                .loyaltyPoints(new LoyaltyPoints(persistenceEntity.getLoyaltyPoints()))
                .address(toAddressValueObject(persistenceEntity.getAddress()))
                .version(persistenceEntity.getVersion())
                .build();
    }

    private BirthDate toBirthDate(LocalDate birthDate) {
        if(birthDate == null) return null;
        return new BirthDate(birthDate);
    }
}
