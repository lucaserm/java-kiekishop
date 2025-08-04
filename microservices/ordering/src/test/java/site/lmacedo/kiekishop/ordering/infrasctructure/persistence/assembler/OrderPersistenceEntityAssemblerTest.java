package site.lmacedo.kiekishop.ordering.infrasctructure.persistence.assembler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.lmacedo.kiekishop.ordering.domain.model.model.Order;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderItem;
import site.lmacedo.kiekishop.ordering.domain.model.model.OrderTestDataBuilder;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.entity.*;
import site.lmacedo.kiekishop.ordering.infrasctructure.persistence.repository.CustomerPersistenceEntityRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceEntityAssemblerTest {

    @Mock
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @InjectMocks
    private OrderPersistenceEntityAssembler assembler;

    @BeforeEach
    void setup() {
        Mockito.when(customerPersistenceEntityRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(a -> {
                    UUID customerId = a.getArgument(0, UUID.class);
                    return CustomerPersistenceEntityTestDataBuilder.aCustomer().id(customerId).build();
                });
    }

    @Test
    void shouldConvertToDomain() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistenceEntity = assembler.fromDomain(order);
        Assertions.assertThat(orderPersistenceEntity).satisfies(
                s -> {
                    Assertions.assertThat(s.getId()).isEqualTo(order.id().value().toLong());
                    Assertions.assertThat(s.getCustomerId()).isEqualTo(order.customerId().value());
                    Assertions.assertThat(s.getTotalAmount()).isEqualByComparingTo(order.totalAmount().value());
                    Assertions.assertThat(s.getTotalItems()).isEqualTo(order.totalItems().value());
                    Assertions.assertThat(s.getStatus()).isEqualTo(order.status().toString());
                    Assertions.assertThat(s.getPaymentMethod()).isEqualTo(order.paymentMethod().toString());
                    Assertions.assertThat(s.getPlacedAt()).isEqualTo(order.placedAt());
                    Assertions.assertThat(s.getPaidAt()).isEqualTo(order.paidAt());
                    Assertions.assertThat(s.getCanceledAt()).isEqualTo(order.canceledAt());
                    Assertions.assertThat(s.getReadyAt()).isEqualTo(order.readyAt());
                }
        );
    }

    @Test
    void givenOrderWithNoItems_shouldRemovePersistenceEntityItems(){
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();

        assembler.merge(orderPersistenceEntity, order);

        Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();
    }

    @Test
    void givenOrderWithItems_shouldAddToPersistenceEntity() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().items(new HashSet<>()).build();

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems()).isEmpty();

        assembler.merge(persistenceEntity, order);

        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems().size()).isEqualTo(order.items().size());
    }

    @Test
    void givenOrderWithItems_whenMerge_shouldMergeCorrectly() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();

        Assertions.assertThat(order.items()).hasSizeGreaterThan(1);

        Set<OrderItemPersistenceEntity> items = order.items().stream().map(assembler::fromDomain).collect(Collectors.toSet());

        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().items(items).build();

        OrderItem orderItem = order.items().iterator().next();
        order.removeItem(orderItem.id());

        assembler.merge(persistenceEntity, order);

        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems().size()).isEqualTo(order.items().size());
    }
}