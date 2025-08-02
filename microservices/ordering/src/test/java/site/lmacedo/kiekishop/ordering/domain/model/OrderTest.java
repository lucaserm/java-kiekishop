package site.lmacedo.kiekishop.ordering.domain.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.valueobject.ProductName;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.CustomerId;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.ProductId;

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

}