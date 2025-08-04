package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.CustomerPersistenceEntity;

import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
}
