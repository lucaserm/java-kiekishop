package site.lmacedo.kiekishop.ordering.infrastructure.persistence.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.order.Order;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderItem;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Address;
import site.lmacedo.kiekishop.ordering.domain.model.order.Billing;
import site.lmacedo.kiekishop.ordering.domain.model.order.Shipping;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceEntityAssembler {

    private final CustomerPersistenceEntityRepository customerRepository;

    public OrderPersistenceEntity fromDomain(Order order) {
        return this.merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order) {
        orderPersistenceEntity.setId(order.id().value().toLong());
        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setStatus(order.status().name());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod().name());
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCanceledAt(order.canceledAt());
        orderPersistenceEntity.setReadyAt(order.readyAt());
        orderPersistenceEntity.setVersion(order.version());
        orderPersistenceEntity.setBilling(toBillingEmbeddable(order.billing()));
        orderPersistenceEntity.setShipping(toShippingEmbeddable(order.shipping()));

        Set<OrderItemPersistenceEntity> mergedItems = mergeItems(orderPersistenceEntity, order);
        orderPersistenceEntity.replaceItems(mergedItems);

        var customer = customerRepository.getReferenceById(order.customerId().value());
        orderPersistenceEntity.setCustomer(customer);

        return orderPersistenceEntity;
    }

    private Set<OrderItemPersistenceEntity> mergeItems(OrderPersistenceEntity orderPersistenceEntity, Order order) {
        Set<OrderItem> newOrUpdatedItems = order.items();
        if (newOrUpdatedItems == null || newOrUpdatedItems.isEmpty()) {
            return new HashSet<>();
        }

        Set<OrderItemPersistenceEntity> existingItems = orderPersistenceEntity.getItems();
        if (existingItems == null || existingItems.isEmpty()) {
            return newOrUpdatedItems.stream()
                    .map(this::fromDomain)
                    .collect(Collectors.toSet());
        }

        Map<Long, OrderItemPersistenceEntity> existingItemMap = existingItems.stream()
                .collect(Collectors.toMap(OrderItemPersistenceEntity::getId, item -> item));

        return newOrUpdatedItems.stream().map(orderItem -> {
            OrderItemPersistenceEntity itemPersistence = existingItemMap.getOrDefault(orderItem.orderId().value().toLong(), new OrderItemPersistenceEntity());
            return merge(itemPersistence, orderItem);
        }).collect(Collectors.toSet());
    }

    public OrderItemPersistenceEntity fromDomain(OrderItem orderItem) {
        return merge(new OrderItemPersistenceEntity(), orderItem);
    }

    public OrderItemPersistenceEntity merge(OrderItemPersistenceEntity orderItemPersistenceEntity, OrderItem orderItem) {
        orderItemPersistenceEntity.setId(orderItem.id().value().toLong());
        orderItemPersistenceEntity.setProductId(orderItem.productId().value());
        orderItemPersistenceEntity.setProductName(orderItem.productName().value());
        orderItemPersistenceEntity.setPrice(orderItem.price().value());
        orderItemPersistenceEntity.setQuantity(orderItem.quantity().value());
        orderItemPersistenceEntity.setTotalAmount(orderItem.totalAmount().value());
        return orderItemPersistenceEntity;
    }

    private ShippingEmbeddable toShippingEmbeddable(Shipping shipping) {
        if (shipping == null) return null;
        RecipientEmbeddable recipientEmbeddable = RecipientEmbeddable.builder()
                .firstName(shipping.recipient().fullName().firstName())
                .lastName(shipping.recipient().fullName().lastName())
                .document(shipping.recipient().document().value())
                .phone(shipping.recipient().phone().value())
                .build();
        return ShippingEmbeddable.builder()
                .cost(shipping.cost().value())
                .expectedDate(shipping.expectedDate())
                .recipient(recipientEmbeddable)
                .address(toAddressEmbeddable(shipping.address()))
                .build();
    }

    private BillingEmbeddable toBillingEmbeddable(Billing billing) {
        if (billing == null) return null;
        return BillingEmbeddable.builder()
                .firstName(billing.fullName().firstName())
                .lastName(billing.fullName().lastName())
                .document(billing.document().value())
                .phone(billing.phone().value())
                .email(billing.email().value())
                .address(toAddressEmbeddable(billing.address()))
                .build();
    }

    public static AddressEmbeddable toAddressEmbeddable(Address address) {
        return AddressEmbeddable.builder()
                .street(address.street())
                .state(address.state())
                .city(address.city())
                .zipCode(address.zipCode().value())
                .number(address.number())
                .complement(address.complement())
                .neighborhood(address.neighborhood())
                .build();
    }
}
