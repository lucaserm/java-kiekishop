package site.lmacedo.kiekishop.ordering.domain.model.order;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.commons.*;

import java.util.Objects;

@Builder
public record Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {
    public Billing {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(email);
        Objects.requireNonNull(address);
    }
}