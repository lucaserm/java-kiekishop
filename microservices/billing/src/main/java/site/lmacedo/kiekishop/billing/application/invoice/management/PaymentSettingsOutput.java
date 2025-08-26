package site.lmacedo.kiekishop.billing.application.invoice.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSettingsOutput {
    private UUID id;
    private UUID creditCardId;
//    private PaymentMethod method;
}
