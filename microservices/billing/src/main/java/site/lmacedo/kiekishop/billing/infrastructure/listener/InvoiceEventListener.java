package site.lmacedo.kiekishop.billing.infrastructure.listener;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceCanceledEvent;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceIssuedEvent;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoicePaidEvent;

@Component
public class InvoiceEventListener {

    @EventListener
    public void listen(InvoiceIssuedEvent event) {

    }

    @EventListener
    public void listen(InvoiceCanceledEvent event) {

    }

    @EventListener
    public void listen(InvoicePaidEvent event) {

    }
}