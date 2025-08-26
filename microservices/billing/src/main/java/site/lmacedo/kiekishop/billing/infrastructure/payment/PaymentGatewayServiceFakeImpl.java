package site.lmacedo.kiekishop.billing.infrastructure.payment;


import org.springframework.stereotype.Service;
import site.lmacedo.kiekishop.billing.domain.model.invoice.PaymentMethod;
import site.lmacedo.kiekishop.billing.domain.model.invoice.payment.Payment;
import site.lmacedo.kiekishop.billing.domain.model.invoice.payment.PaymentGatewayService;
import site.lmacedo.kiekishop.billing.domain.model.invoice.payment.PaymentRequest;
import site.lmacedo.kiekishop.billing.domain.model.invoice.payment.PaymentStatus;

import java.util.UUID;

@Service
public class PaymentGatewayServiceFakeImpl implements PaymentGatewayService {

    @Override
    public Payment capture(PaymentRequest request) {
        return Payment.builder()
                .invoiceId(request.getInvoiceId())
                .status(PaymentStatus.PAID)
                .method(request.getMethod())
                .gatewayCode(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public Payment findByCode(String gatewayCode) {
        return Payment.builder()
                .invoiceId(UUID.randomUUID())
                .status(PaymentStatus.PAID)
                .method(PaymentMethod.GATEWAY_BALANCE)
                .gatewayCode(UUID.randomUUID().toString())
                .build();
    }
}