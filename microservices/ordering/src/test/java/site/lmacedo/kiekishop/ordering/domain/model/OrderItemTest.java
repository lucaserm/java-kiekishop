package site.lmacedo.kiekishop.ordering.domain.model;

import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Money;
import site.lmacedo.kiekishop.ordering.domain.valueobject.ProductName;
import site.lmacedo.kiekishop.ordering.domain.valueobject.Quantity;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.OrderId;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.ProductId;

class OrderItemTest {
    @Test
    void shouldGenerate() {
        OrderItem orderItem = OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse pad"))
                .price(new Money("100"))
                .build();
    }

}