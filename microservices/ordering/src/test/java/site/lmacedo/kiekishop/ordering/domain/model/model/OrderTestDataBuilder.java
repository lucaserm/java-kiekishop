package site.lmacedo.kiekishop.ordering.domain.model.model;

import site.lmacedo.kiekishop.ordering.domain.model.valueobject.*;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {
    private CustomerId customerId = new CustomerId();

    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

    private Shipping shipping = aShipping();
    private Billing billing = aBilling();

    private boolean withItems = true;

    private OrderStatus status = OrderStatus.DRAFT;


    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        Product mouse = ProductTestDataBuilder.aProductAltMouse().build();
        Product notebook = ProductTestDataBuilder.aProduct().build();
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(
                    mouse,
                    new Quantity(1)
            );

            order.addItem(
                    notebook,
                    new Quantity(2)
            );
        }

        switch (this.status) {
            case DRAFT -> {
                break;
            }
            case PLACED -> order.place();
            case PAID -> {
                order.place();
                order.markAsPaid();
            }
            case READY -> {
                order.place();
                order.markAsPaid();
                order.markAsReady();
            }
            case CANCELED -> {
                order.place();
                order.markAsPaid();
                order.markAsReady();
                order.cancel();
            }
        }

        return order;
    }

    public static Shipping aShipping() {
        return Shipping.builder()
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .address(anAddress())
                .recipient(
                        Recipient.builder()
                                .document(new Document("225-09-1992"))
                                .phone(new Phone("123-111-9911"))
                                .fullName(new FullName("John", "Doe"))
                                .build()
                )
                .build();
    }

    public static Billing aBilling() {
        return Billing.builder()
                .address(anAddress())
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .email(new Email("john.doe@gmail.com"))
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

    public static Shipping aShippingAlt() {
        return Shipping.builder()
                .cost(new Money("20.00"))
                .expectedDate(LocalDate.now().plusWeeks(2))
                .address(anAddressAlt())
                .recipient(
                        Recipient.builder()
                                .fullName(new FullName("Jane", "Doe"))
                                .document(new Document("552-11-4333"))
                                .phone(new Phone("54-454-1144"))
                                .build()
                )
                .build();
    }

    public static Address anAddressAlt() {
        return Address.builder()
                .street("Sansome Street")
                .number("875")
                .neighborhood("Sansome")
                .complement("Apt. 11")
                .city("San Francisco")
                .state("California")
                .zipCode(new ZipCode("08040"))
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

    public OrderTestDataBuilder shipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billing(Billing billing) {
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
