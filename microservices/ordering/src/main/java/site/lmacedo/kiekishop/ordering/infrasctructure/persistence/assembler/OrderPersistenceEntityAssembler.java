package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Billing;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Shipping;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.AddressEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.BillingEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.RecipientEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.embedabble.ShippingEmbeddable;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;

@Component
public class OrderPersistenceEntityAssembler {
    public OrderPersistenceEntity fromDomain(Order order) {
        return this.merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order) {
        orderPersistenceEntity.setId(order.id().value().toLong());
        orderPersistenceEntity.setCustomerId(order.customerId().value());
        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setStatus(order.status().name());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod().name());
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCanceledAt(order.canceledAt());
        orderPersistenceEntity.setReadyAt(order.readyAt());
        orderPersistenceEntity.setVersion(order.version());

        Billing billing = order.billing();
        if (billing != null) {
            AddressEmbeddable addressEmbeddable = AddressEmbeddable.builder()
                    .street(billing.address().street())
                    .state(billing.address().state())
                    .city(billing.address().city())
                    .zipCode(billing.address().zipCode().value())
                    .number(billing.address().number())
                    .complement(billing.address().complement())
                    .neighborhood(billing.address().neighborhood())
                    .build();
            BillingEmbeddable billingEmbeddable = BillingEmbeddable.builder()
                    .firstName(billing.fullName().firstName())
                    .lastName(billing.fullName().lastName())
                    .document(billing.document().value())
                    .phone(billing.phone().value())
                    .email(billing.email().value())
                    .address(addressEmbeddable)
                    .build();
            orderPersistenceEntity.setBilling(billingEmbeddable);
        }

        Shipping shipping = order.shipping();
        if(shipping != null) {
            AddressEmbeddable addressEmbeddable = AddressEmbeddable.builder()
                    .street(shipping.address().street())
                    .state(shipping.address().state())
                    .city(shipping.address().city())
                    .zipCode(shipping.address().zipCode().value())
                    .number(shipping.address().number())
                    .complement(shipping.address().complement())
                    .neighborhood(shipping.address().neighborhood())
                    .build();
            RecipientEmbeddable recipientEmbeddable = RecipientEmbeddable.builder()
                    .firstName(shipping.recipient().fullName().firstName())
                    .lastName(shipping.recipient().fullName().lastName())
                    .document(shipping.recipient().document().value())
                    .phone(shipping.recipient().phone().value())
                    .build();
            ShippingEmbeddable shippingEmbeddable = ShippingEmbeddable.builder()
                    .cost(shipping.cost().value())
                    .expectedDate(shipping.expectedDate())
                    .recipient(recipientEmbeddable)
                    .address(addressEmbeddable)
                    .build();
            orderPersistenceEntity.setShipping(shippingEmbeddable);
        }

        return orderPersistenceEntity;
    }
}
