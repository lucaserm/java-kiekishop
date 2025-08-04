package site.lmacedo.kiekishop.ordering.domain.model.order;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Address;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Money;

import java.time.LocalDate;
import java.util.Objects;

@Builder(toBuilder = true)
public record Shipping(Money cost, LocalDate expectedDate, Recipient recipient, Address address) {
    public Shipping {
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(address);
        Objects.requireNonNull(cost);
        Objects.requireNonNull(expectedDate);
    }
}
