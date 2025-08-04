package site.lmacedo.kiekishop.ordering.infrastructure.shipping.client.rapidex;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.ShippingCostService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kiekishop.integrations.shipping.provider", havingValue = "RAPIDEX")
public class ShippingCostServiceRapidexImpl implements ShippingCostService {

    private final RapiDexAPIClient rapiDexAPIClient;

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        DeliveryCostResponse response = rapiDexAPIClient.calculate(
                new DeliveryCostRequest(
                        request.origin().value(),
                        request.destination().value()
                )
        );

        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(response.getEstimatedDaysToDeliver());

        return CalculationResult.builder()
                .cost(new Money(response.getDeliveryCost()))
                .expectedDate(expectedDeliveryDate)
                .build();
    }
}
