package site.lmacedo.kiekishop.billing.domain.model.invoice.payment;

public interface PaymentGatewayService {
    Payment capture(PaymentRequest request);
    Payment findByCode(String gatewayCode);
}