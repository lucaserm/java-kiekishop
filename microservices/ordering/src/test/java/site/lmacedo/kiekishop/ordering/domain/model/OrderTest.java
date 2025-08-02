package site.lmacedo.kiekishop.ordering.domain.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.exception.OrderStatusCanNotBeChanged;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.valueobject.ProductName;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.ProductId;

import java.util.Set;

class OrderTest {
    @Test
    void shouldGenerate() {
        Order draft = Order.draft(new CustomerId());
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(productId, new ProductName("Mouse"), new Money("100"), new Quantity(1));
        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                i -> Assertions.assertThat(i.id()).isNotNull(),
                i -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                i -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse")),
                i -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    void shouldGenerateExceptionWhenTryToChangeItemSet() {

        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(productId, new ProductName("Mouse"), new Money("100"), new Quantity(1));

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(productId, new ProductName("Mouse"), new Money("100"), new Quantity(2));
        order.addItem(productId, new ProductName("RAM Memory"), new Money("50"), new Quantity(1));

        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3));
        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("250"));
    }

    @Test
    void givenDraftOrder_whenPlace_shouldChangeToPlaced(){
        Order order = Order.draft(new CustomerId());
        Assertions.assertThat(order.isDraft()).isTrue();
        order.place();
        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    void givenPlacedOrder_whenTryToPlace_shouldGenerateException() {
        Order order = Order.draft(new CustomerId());
        order.place();
        Assertions.assertThatExceptionOfType(OrderStatusCanNotBeChanged.class)
                .isThrownBy(order::place);
    }
}