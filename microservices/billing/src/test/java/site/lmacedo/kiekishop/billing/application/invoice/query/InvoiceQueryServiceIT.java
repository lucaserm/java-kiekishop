package site.lmacedo.kiekishop.billing.application.invoice.query;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.billing.domain.model.invoice.Invoice;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceRepository;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceTestDataBuilder;
import site.lmacedo.kiekishop.billing.domain.model.invoice.PaymentMethod;

@SpringBootTest
@Transactional
class InvoiceQueryServiceIT {

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void shouldFindByOrderId() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);
        InvoiceOutput invoiceOutput = invoiceQueryService.findByOrderId(invoice.getOrderId());

        Assertions.assertThat(invoiceOutput.getId()).isEqualTo(invoice.getId());
    }

}