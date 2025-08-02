package site.lmacedo.kiekishop.ordering.domain.valueobject;

import site.lmacedo.kiekishop.ordering.domain.validator.FieldValidations;

public record Phone(String value) {
    public Phone {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
