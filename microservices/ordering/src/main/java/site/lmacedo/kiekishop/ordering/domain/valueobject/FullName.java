package site.lmacedo.kiekishop.ordering.domain.valueobject;

import site.lmacedo.kiekishop.ordering.domain.validator.FieldValidations;

public record FullName(String firstName, String lastName) {
    public FullName(String firstName, String lastName) {
        FieldValidations.requiresNonBlank(firstName, "First name cannot be blank.");
        FieldValidations.requiresNonBlank(lastName,"Last name cannot be blank.");
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
