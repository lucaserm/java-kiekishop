package site.lmacedo.kiekishop.ordering.domain.model.commons;

import site.lmacedo.kiekishop.ordering.domain.model.FieldValidations;

public record Phone(String value) {
    public Phone {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
