package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.disassembler;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderStatus;
import site.lmacedo.kiekishop.ordering.domain.model.model.PaymentMethod;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.*;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.BillingEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.ShippingEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;

import java.util.HashSet;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomain(OrderPersistenceEntity orderPersistenceEntity) {
        Shipping shipping = null;
        ShippingEmbeddable shippingEmbeddable = orderPersistenceEntity.getShipping();
        if(shippingEmbeddable != null) {
            FullName fullName = new FullName(shippingEmbeddable.getRecipient().getFirstName(),
                    shippingEmbeddable.getRecipient().getLastName());
            Recipient recipient = Recipient.builder()
                    .fullName(fullName)
                    .document(new Document(shippingEmbeddable.getRecipient().getDocument()))
                    .phone(new Phone(shippingEmbeddable.getRecipient().getPhone()))
                    .build();
            Address address = Address.builder()
                    .street(shippingEmbeddable.getAddress().getStreet())
                    .complement(shippingEmbeddable.getAddress().getComplement())
                    .number(shippingEmbeddable.getAddress().getNumber())
                    .neighborhood(shippingEmbeddable.getAddress().getNeighborhood())
                    .city(shippingEmbeddable.getAddress().getCity())
                    .state(shippingEmbeddable.getAddress().getState())
                    .zipCode(new ZipCode(shippingEmbeddable.getAddress().getZipCode()))
                    .build();

            shipping = Shipping.builder()
                    .cost(new Money(shippingEmbeddable.getCost()))
                    .expectedDate(shippingEmbeddable.getExpectedDate())
                    .recipient(recipient)
                    .address(address)
                    .build();
        }

        Billing billing = null;
        BillingEmbeddable billingEmbeddable = orderPersistenceEntity.getBilling();

        if (billingEmbeddable != null) {
            Address address = Address.builder()
                    .street(billingEmbeddable.getAddress().getStreet())
                    .complement(billingEmbeddable.getAddress().getComplement())
                    .number(billingEmbeddable.getAddress().getNumber())
                    .neighborhood(billingEmbeddable.getAddress().getNeighborhood())
                    .city(billingEmbeddable.getAddress().getCity())
                    .state(billingEmbeddable.getAddress().getState())
                    .zipCode(new ZipCode(billingEmbeddable.getAddress().getZipCode()))
                    .build();

            billing = Billing.builder()
                    .fullName(new FullName(billingEmbeddable.getFirstName(), billingEmbeddable.getLastName()))
                    .document(new Document(billingEmbeddable.getDocument()))
                    .phone(new Phone(billingEmbeddable.getPhone()))
                    .email(new Email(billingEmbeddable.getEmail()))
                    .address(address)
                    .build();
        }

        return Order.existing()
                .id(new OrderId(orderPersistenceEntity.getId()))
                .customerId(new CustomerId(orderPersistenceEntity.getCustomerId()))
                .totalAmount(new Money(orderPersistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(orderPersistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(orderPersistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(orderPersistenceEntity.getPaymentMethod()))
                .placedAt(orderPersistenceEntity.getPlacedAt())
                .paidAt(orderPersistenceEntity.getPaidAt())
                .canceledAt(orderPersistenceEntity.getCanceledAt())
                .readyAt(orderPersistenceEntity.getReadyAt())
                .items(new HashSet<>())
                .version(orderPersistenceEntity.getVersion())
                .shipping(shipping)
                .billing(billing)
                .build();
    }
}
