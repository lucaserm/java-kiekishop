package site.lmacedo.kiekishop.ordering.infrastructure.persistence.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.domain.model.customer.Customer;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCart;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import site.lmacedo.kiekishop.ordering.domain.model.customer.CustomerId;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.SpringDataAuditingConfig;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomersPersistenceProvider;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartsPersistenceProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest
@Import({
        ShoppingCartsPersistenceProvider.class,
        ShoppingCartPersistenceEntityAssembler.class,
        ShoppingCartPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class ShoppingCartsPersistenceProviderIT {

    private ShoppingCartsPersistenceProvider persistenceProvider;
    private CustomersPersistenceProvider customersPersistenceProvider;
    private ShoppingCartPersistenceEntityRepository entityRepository;

    @Autowired
    public ShoppingCartsPersistenceProviderIT(ShoppingCartsPersistenceProvider persistenceProvider,
                                              CustomersPersistenceProvider customersPersistenceProvider,
                                              ShoppingCartPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.entityRepository = entityRepository;
    }

    @BeforeEach
    void setup() {
        if (!customersPersistenceProvider.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customersPersistenceProvider.add(
                    CustomerTestDataBuilder.existingCustomer().build()
            );
        }
    }

    @Test
    void shouldAddAndFindShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        assertThat(shoppingCart.version()).isNull();

        persistenceProvider.add(shoppingCart);

        assertThat(shoppingCart.version()).isNotNull().isEqualTo(0L);

        ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
        assertThat(foundCart.totalItems().value()).isEqualTo(3);
    }

    @Test
    void shouldRemoveShoppingCartById() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart.id());

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
        assertThat(entityRepository.findById(shoppingCart.id().value())).isEmpty();
    }

    @Test
    void shouldRemoveShoppingCartByEntity() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart);

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
    }

    @Test
    void shouldFindShoppingCartByCustomerId() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart()
                .customerId(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)
                .build();
        persistenceProvider.add(shoppingCart);

        ShoppingCart foundCart = persistenceProvider.ofCustomer(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID).orElseThrow();

        assertThat(foundCart).isNotNull();
        assertThat(foundCart.customerId()).isEqualTo(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID);
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
    }

    @Test
    void shouldCorrectlyCountShoppingCarts() {
        long initialCount = persistenceProvider.count();

        ShoppingCart cart1 = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(cart1);

        Customer otherCustomer = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customersPersistenceProvider.add(otherCustomer);

        ShoppingCart cart2 = ShoppingCartTestDataBuilder.aShoppingCart().customerId(otherCustomer.id()).build();
        persistenceProvider.add(cart2);

        long finalCount = persistenceProvider.count();

        assertThat(finalCount).isEqualTo(initialCount + 2);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddAndFindWhenNoTransaction() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        persistenceProvider.add(shoppingCart);

        assertThatNoException().isThrownBy(() -> {
            ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
            assertThat(foundCart).isNotNull();
        });
    }
}