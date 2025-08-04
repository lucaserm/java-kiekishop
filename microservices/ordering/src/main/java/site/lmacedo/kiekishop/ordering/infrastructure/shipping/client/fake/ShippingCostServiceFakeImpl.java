package site.lmacedo.kiekishop.ordering.infrastructure.shipping.client.fake;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.ShippingCostService;

import java.time.LocalDate;

@Component
@ConditionalOnProperty(name = "kiekishop.integrations.shipping.provider", havingValue = "FAKE")
public class ShippingCostServiceFakeImpl implements ShippingCostService {
    @Override
    public CalculationResult calculate(CalculationRequest request) {
        return new CalculationResult(
                new Money("20"),
                LocalDate.now().plusDays(5)
        );
    }
}
