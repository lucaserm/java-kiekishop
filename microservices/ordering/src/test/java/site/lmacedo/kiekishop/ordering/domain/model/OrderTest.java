package site.lmacedo.kiekishop.ordering.domain.model;


import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.valueobject.id.CustomerId;

class OrderTest {
    @Test
    void shouldGenerate() {
        Order draft = Order.draft(new CustomerId());
    }

}