package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.config.SpringDataAuditingConfig;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SpringDataAuditingConfig.class)
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository repository;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository repository) {
        this.repository = repository;
    }

    @Test
    void shouldPersist() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        repository.saveAndFlush(entity);
        Assertions.assertThat(repository.existsById(entity.getId())).isTrue();
    }

    @Test
    void shouldCount() {
        long ordersCount = repository.count();
        Assertions.assertThat(ordersCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        entity = repository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}