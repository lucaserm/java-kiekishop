package site.lmacedo.kiekishop.ordering.domain.model.valueobject.id;

import site.lmacedo.kiekishop.ordering.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ShoppingCartItemId(UUID value) {

    public ShoppingCartItemId {
        Objects.requireNonNull(value);
    }

    public ShoppingCartItemId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public ShoppingCartItemId(String value) {
        this(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}