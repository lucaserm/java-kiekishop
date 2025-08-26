package site.lmacedo.kiekishop.billing.application.invoice.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.lmacedo.kiekishop.billing.domain.model.invoice.PaymentMethod;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSettingsInput {
    private PaymentMethod method;
    private UUID creditCardId;
}