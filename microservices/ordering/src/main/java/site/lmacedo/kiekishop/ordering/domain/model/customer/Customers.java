package site.lmacedo.kiekishop.ordering.domain.model.customer;

import site.lmacedo.kiekishop.ordering.domain.model.commons.Email;
import site.lmacedo.kiekishop.ordering.domain.model.Repository;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {
    Optional<Customer> ofEmail(Email email);
    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);
}
