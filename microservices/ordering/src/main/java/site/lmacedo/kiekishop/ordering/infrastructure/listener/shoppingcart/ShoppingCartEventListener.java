package site.lmacedo.kiekishop.ordering.infrastructure.listener.shoppingcart;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartCreatedEvent;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartEmptiedEvent;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartItemAddedEvent;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartItemRemovedEvent;

@Component
public class ShoppingCartEventListener {

    @EventListener
    public void listen(ShoppingCartCreatedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartEmptiedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartItemAddedEvent event) {

    }

    @EventListener
    public void listen(ShoppingCartItemRemovedEvent event) {

    }

}
