package site.lmacedo.kiekishop.ordering.domain.model.customer;


import site.lmacedo.kiekishop.ordering.domain.model.commons.Email;
import site.lmacedo.kiekishop.ordering.domain.model.commons.FullName;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(CustomerId customerId,
                                      OffsetDateTime registeredAt,
                                      FullName fullName,
                                      Email email) {
}