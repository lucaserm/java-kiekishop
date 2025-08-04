package site.lmacedo.kiekishop.ordering.domain.model.product;

import site.lmacedo.kiekishop.ordering.domain.model.FieldValidations;

public record ProductName(String value) {
    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
