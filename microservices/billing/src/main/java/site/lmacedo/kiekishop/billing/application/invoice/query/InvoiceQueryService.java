package site.lmacedo.kiekishop.billing.application.invoice.query;

public interface InvoiceQueryService {
    InvoiceOutput findByOrderId(String orderId);
}