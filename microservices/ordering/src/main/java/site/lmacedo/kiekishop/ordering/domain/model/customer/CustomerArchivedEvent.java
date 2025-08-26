package site.lmacedo.kiekishop.ordering.domain.model.customer;


import java.time.OffsetDateTime;

public record CustomerArchivedEvent(CustomerId customerId, OffsetDateTime archivedAt) {
}
