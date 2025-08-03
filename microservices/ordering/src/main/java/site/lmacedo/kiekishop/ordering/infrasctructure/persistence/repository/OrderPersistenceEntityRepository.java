package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {
}
