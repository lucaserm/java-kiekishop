package site.lmacedo.kiekishop.billing.application.invoice.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.lmacedo.kiekishop.billing.application.invoice.management.PayerData;
import site.lmacedo.kiekishop.billing.application.invoice.management.PaymentSettingsOutput;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutput {
    private UUID id;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime issuedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime expiresAt;
    private BigDecimal totalAmount;
//    private InvoiceStatus status;
    private PayerData payer;
    private PaymentSettingsOutput paymentSettings;
}