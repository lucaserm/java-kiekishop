package site.lmacedo.kiekishop.ordering.domain.model.repository;

import site.lmacedo.kiekishop.ordering.domain.model.model.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.Email;
import site.lmacedo.kiekishop.ordering.domain.model.valueobject.id.CustomerId;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {
    Optional<Customer> ofEmail(Email email);
    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);
}
