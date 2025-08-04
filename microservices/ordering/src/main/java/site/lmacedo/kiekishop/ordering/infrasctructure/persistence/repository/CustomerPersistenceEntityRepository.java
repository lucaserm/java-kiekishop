package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.CustomerPersistenceEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
    Optional<CustomerPersistenceEntity> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
