package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import site.lmacedo.kiekishop.ordering.domain.model.validator.FieldValidations;

public record Phone(String value) {
    public Phone {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
