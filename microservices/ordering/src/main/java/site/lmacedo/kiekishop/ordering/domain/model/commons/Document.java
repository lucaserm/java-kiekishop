package site.lmacedo.kiekishop.ordering.domain.model.commons;

import site.lmacedo.kiekishop.ordering.domain.model.FieldValidations;

public record Document(String value) {
    public Document {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
