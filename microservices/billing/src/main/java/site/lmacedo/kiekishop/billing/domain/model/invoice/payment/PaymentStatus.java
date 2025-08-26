package site.lmacedo.kiekishop.billing.domain.model.invoice.payment;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    FAILED,
    REFUNDED,
    PAID
}