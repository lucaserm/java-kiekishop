package site.lmacedo.kiekishop.ordering.domain.model.repository;

import site.lmacedo.kiekishop.ordering.domain.model.model.AggregateRoot;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<id>, id> {
    Optional<T> ofId(id id);
    boolean exists(id id);
    void add(T aggregateRoot);
    long count();
}
