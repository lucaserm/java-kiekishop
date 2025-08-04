package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.model.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.*;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.CustomerPersistenceEntity;

import java.time.LocalDate;

import static site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler.OrderPersistenceEntityDisassembler.toAddressValueObject;

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
