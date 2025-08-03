package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import site.lmacedo.kiekishop.ordering.domain.model.validator.FieldValidations;

public record ProductName(String value) {
    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
