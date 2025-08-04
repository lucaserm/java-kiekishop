package site.lmacedo.kiekishop.ordering.domain.model.order.shipping;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;
import site.lmacedo.kiekishop.ordering.domain.model.commons.ZipCode;

import java.time.LocalDate;

public interface ShippingCostService {
    CalculationResult calculate(CalculationRequest request);

    @Builder
    record CalculationRequest(ZipCode origin, ZipCode destination) {}

    @Builder
    record CalculationResult(Money cost, LocalDate expectedDate) {}
}
