package site.lmacedo.kiekishop.ordering.domain.model.commons;

import java.io.Serializable;
import java.util.Objects;

public record Quantity(Integer value) implements Serializable, Comparable<Quantity> {
    public static final Quantity ZERO = new Quantity(0);

    public Quantity {
        Objects.requireNonNull(value);
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(value() + quantity.value());
    }

    @Override
    public int compareTo(Quantity other) {
        return value().compareTo(other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value());
    }
}
