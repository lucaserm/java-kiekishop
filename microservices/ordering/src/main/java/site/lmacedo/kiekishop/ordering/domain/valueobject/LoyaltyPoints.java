package site.lmacedo.kiekishop.ordering.domain.valueobject;

import java.util.Objects;

import static site.lmacedo.kiekishop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTYPOINTS_IS_NEGATIVE;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints>{
    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints {
        Objects.requireNonNull(value);
        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTYPOINTS_IS_NEGATIVE);
        }
    }

    public LoyaltyPoints add(Integer value) {
        return add(new LoyaltyPoints(value));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        if(loyaltyPoints.value() <= 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTYPOINTS_IS_NEGATIVE);
        }
        return new LoyaltyPoints(this.value() + loyaltyPoints.value());
    }

    @Override
    public String toString() {
        return value.toString();
    }


    @Override
    public int compareTo(LoyaltyPoints o) {
        return this.value().compareTo(o.value());
    }
}
