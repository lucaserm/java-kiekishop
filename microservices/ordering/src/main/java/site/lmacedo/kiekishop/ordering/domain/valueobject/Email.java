package site.lmacedo.kiekishop.ordering.domain.valueobject;

import site.lmacedo.kiekishop.ordering.domain.validator.FieldValidations;

import static site.lmacedo.kiekishop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

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
