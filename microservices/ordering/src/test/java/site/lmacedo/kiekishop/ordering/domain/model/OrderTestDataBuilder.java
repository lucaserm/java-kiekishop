package site.lmacedo.kiekishop.ordering.domain.model;

import site.lmacedo.kiekishop.ordering.domain.valueobject.*;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.ProductId;

import java.time.LocalDate;

public class OrderTestDataBuilder {
    private CustomerId customerId = new CustomerId();

    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

    private Money shippingCost = new Money("10.00");
    private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);

    private ShippingInfo shipping = aShippingInfo();
    private BillingInfo billing = aBillingInfo();

    private boolean withItems = true;

    private OrderStatus status = OrderStatus.DRAFT;


    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeBilling(billing);
        order.changeShipping(shipping, shippingCost, expectedDeliveryDate);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(
                    new ProductId(),
                    new ProductName("Mouse"),
                    new Money("100"),
                    new Quantity(1)
            );

            order.addItem(
                    new ProductId(),
                    new ProductName("Notebook X11"),
                    new Money("1000"),
                    new Quantity(2)
            );
        }

        switch (this.status) {
            case PLACED -> order.place();
            case PAID -> {
                order.place();
                order.markAsPaid();
            }
        }

        return order;
    }

    public static ShippingInfo aShippingInfo() {
        return ShippingInfo.builder()
                .address(anAddress())
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .build();
    }


    public static BillingInfo aBillingInfo() {
        return BillingInfo.builder()
                .address(anAddress())
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .complement("Apt. 11")
                .city("Montfort")
                .state("South Carolina")
                .zipCode(new ZipCode("12345"))
                .build();
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public OrderTestDataBuilder shipping(ShippingInfo shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billing(BillingInfo billing) {
        this.billing = billing;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }
}
