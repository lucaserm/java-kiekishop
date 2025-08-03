package site.lmacedo.kiekishop.ordering.domain.model.repository;

import site.lmacedo.kiekishop.ordering.domain.model.model.AggregateRoot;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID> extends Repository<T, ID> {
    void remove(T t);
    void remove(ID id);
}
