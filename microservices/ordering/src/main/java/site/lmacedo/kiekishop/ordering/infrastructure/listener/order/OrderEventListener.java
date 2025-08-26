package site.lmacedo.kiekishop.ordering.infrastructure.listener.order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderCanceledEvent;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderPaidEvent;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderPlacedEvent;
import site.lmacedo.kiekishop.ordering.domain.model.order.OrderReadyEvent;

@Component
public class OrderEventListener {

    @EventListener
    public void listen(OrderPlacedEvent event) {

    }

    @EventListener
    public void listen(OrderPaidEvent event) {

    }

    @EventListener
    public void listen(OrderReadyEvent event) {

    }

    @EventListener
    public void listen(OrderCanceledEvent event) {

    }

}
