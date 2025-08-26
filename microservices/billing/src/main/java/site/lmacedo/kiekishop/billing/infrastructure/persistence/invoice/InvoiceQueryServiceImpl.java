package site.lmacedo.kiekishop.billing.infrastructure.persistence.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.billing.application.invoice.query.InvoiceOutput;
import site.lmacedo.kiekishop.billing.application.invoice.query.InvoiceQueryService;
import site.lmacedo.kiekishop.billing.application.utility.Mapper;
import site.lmacedo.kiekishop.billing.domain.model.invoice.Invoice;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceNotFoundException;
import site.lmacedo.kiekishop.billing.domain.model.invoice.InvoiceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;
    private final Mapper mapper;

    @Override
    public InvoiceOutput findByOrderId(String orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId).orElseThrow(InvoiceNotFoundException::new);
        return mapper.convert(invoice, InvoiceOutput.class);
    }
}