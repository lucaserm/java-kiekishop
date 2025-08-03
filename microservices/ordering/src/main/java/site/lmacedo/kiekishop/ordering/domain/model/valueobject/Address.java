package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.validator.FieldValidations;

import java.util.Objects;

public record Address(
        String street,
        String complement,
        String number,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {
    @Builder(toBuilder = true)
    public Address {
        FieldValidations.requiresNonBlank(street);
        FieldValidations.requiresNonBlank(number);
        FieldValidations.requiresNonBlank(neighborhood);
        FieldValidations.requiresNonBlank(city);
        FieldValidations.requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}
