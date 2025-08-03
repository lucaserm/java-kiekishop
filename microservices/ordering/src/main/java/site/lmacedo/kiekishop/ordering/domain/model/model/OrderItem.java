package site.lmacedo.kiekishop.ordering.domain.model.model;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Product;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.ProductName;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.OrderItemId;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.ProductId;

import java.util.Objects;

public class OrderItem {
    private OrderItemId id;
    private OrderId orderId;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmount;

    @Builder(builderClassName = "ExistingOrderItemBuilder", builderMethodName = "existing")
    public OrderItem(
            OrderItemId id, OrderId orderId, ProductId productId, ProductName productName,
            Money price, Quantity quantity, Money totalAmount
    ) {
        this.setId(id);
        this.setOrderId(orderId);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
    }

    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew")
    public static OrderItem creatBrandNew(OrderId orderId,
                                          Product product,
                                          Quantity quantity) {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = new OrderItem(
                new OrderItemId(),
                orderId,
                product.id(),
                product.name(),
                product.price(),
                quantity,
                Money.ZERO
        );

        orderItem.recalculateTotals();

        return orderItem;
    }

    void changeQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.setQuantity(quantity);
        this.recalculateTotals();
    }

    public OrderItemId id() {
        return id;
    }

    public OrderId orderId() {
        return orderId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price().multiply(this.quantity()));
    }

    private void setId(OrderItemId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setOrderId(OrderId orderId) {
        Objects.requireNonNull(orderId);
        this.orderId = orderId;
    }

    private void setProductId(ProductId productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
    }

    private void setProductName(ProductName productName) {
        Objects.requireNonNull(productName);
        this.productName = productName;
    }

    private void setPrice(Money price) {
        Objects.requireNonNull(price);
        this.price = price;
    }

    private void setQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OrderItem orderItem = (OrderItem) object;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
