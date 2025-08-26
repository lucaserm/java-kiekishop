package site.lmacedo.kiekishop.billing.domain.model.invoice.payment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import site.lmacedo.kiekishop.billing.domain.model.FieldValidations;
import site.lmacedo.kiekishop.billing.domain.model.invoice.PaymentMethod;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class Payment {
    private String gatewayCode;
    private UUID invoiceId;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(String gatewayCode, UUID invoiceId,
                   PaymentMethod method, PaymentStatus status) {
        FieldValidations.requiresNonBlank(gatewayCode);
        Objects.requireNonNull(invoiceId);
        Objects.requireNonNull(method);
        Objects.requireNonNull(status);
        this.gatewayCode = gatewayCode;
        this.invoiceId = invoiceId;
        this.method = method;
        this.status = status;
    }
}