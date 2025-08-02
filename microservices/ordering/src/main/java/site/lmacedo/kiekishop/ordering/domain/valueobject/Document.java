package site.lmacedo.kiekishop.ordering.domain.valueobject;

import site.lmacedo.kiekishop.ordering.domain.validator.FieldValidations;

public record Document(String value) {
    public Document {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
