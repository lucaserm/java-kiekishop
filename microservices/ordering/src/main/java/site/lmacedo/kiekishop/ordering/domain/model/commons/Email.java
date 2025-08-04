package site.lmacedo.kiekishop.ordering.domain.model.commons;

import site.lmacedo.kiekishop.ordering.domain.model.FieldValidations;

import static site.lmacedo.kiekishop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {
    public Email {
        FieldValidations.requiresNonBlank(value);
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    @Override
    public String toString() {
        return value;
    }
}
