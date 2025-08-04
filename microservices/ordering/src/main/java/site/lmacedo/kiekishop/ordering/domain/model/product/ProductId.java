package site.lmacedo.kiekishop.ordering.domain.model.product;

import site.lmacedo.kiekishop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId {
        Objects.requireNonNull(value);
    }

    public ProductId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
