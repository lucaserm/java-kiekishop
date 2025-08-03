package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity;

import site.lmacedo.kiekishop.ordering.domain.model.utility.IdGenerator;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderPersistenceEntityTestDataBuilder {
    private OrderPersistenceEntityTestDataBuilder () {}

    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalItems(2)
                .totalAmount(new BigDecimal(1000 ))
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD")
                .placedAt(OffsetDateTime.now());
    }
}
