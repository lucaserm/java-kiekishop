package site.lmacedo.kiekishop.ordering.infrastructure.persistence.order;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.*;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.order.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.order.Billing;
import site.lmacedo.kiekishop.ordering.domain.model.order.Recipient;
import site.lmacedo.kiekishop.ordering.domain.model.order.Shipping;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderId;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.commons.AddressEmbeddable;

import java.util.HashSet;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomain(OrderPersistenceEntity persistenceEntity) {
        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .items(new HashSet<>())
                .version(persistenceEntity.getVersion())
                .shipping(toShippingValueObject(persistenceEntity.getShipping()))
                .billing(toBillingValueObject(persistenceEntity.getBilling()))
                .build();
    }

    private Shipping toShippingValueObject(ShippingEmbeddable shippingEmbeddable) {
        if(shippingEmbeddable == null) return null;
        RecipientEmbeddable recipientEmbeddable = shippingEmbeddable.getRecipient();
        return Shipping.builder()
                .cost(new Money(shippingEmbeddable.getCost()))
                .expectedDate(shippingEmbeddable.getExpectedDate())
                .recipient(
                        Recipient.builder()
                                .fullName(new FullName(recipientEmbeddable.getFirstName(), recipientEmbeddable.getLastName()))
                                .document(new Document(recipientEmbeddable.getDocument()))
                                .phone(new Phone(recipientEmbeddable.getPhone()))
                                .build()
                )
                .address(toAddressValueObject(shippingEmbeddable.getAddress()))
                .build();
    }

    private Billing toBillingValueObject(BillingEmbeddable billingEmbeddable) {
        if(billingEmbeddable == null) return null;
        return Billing.builder()
                .fullName(new FullName(billingEmbeddable.getFirstName(), billingEmbeddable.getLastName()))
                .document(new Document(billingEmbeddable.getDocument()))
                .phone(new Phone(billingEmbeddable.getPhone()))
                .email(new Email(billingEmbeddable.getEmail()))
                .address(toAddressValueObject(billingEmbeddable.getAddress()))
                .build();
    }

    public static Address toAddressValueObject(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }
}
