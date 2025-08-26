package site.lmacedo.kiekishop.ordering.domain.model;

import java.util.List;

public interface DomainEventSource {
    List<Object> domainEvents();
    void clearDomainEvents();
}
