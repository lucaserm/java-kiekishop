package site.lmacedo.kiekishop.ordering.domain.model.repository;

import site.lmacedo.kiekishop.ordering.domain.model.model.AggregateRoot;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<ID>, ID> {
    Optional<T> ofId(ID id);
    boolean exists(ID id);
    void add(T aggregateRoot);
    int count();
}
