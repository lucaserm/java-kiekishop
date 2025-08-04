package site.lmacedo.kiekishop.ordering.domain.model.customer;

import site.lmacedo.kiekishop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {
    public CustomerId {
        Objects.requireNonNull(value);
    }

    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
