package site.lmacedo.kiekishop.ordering.domain.model.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Email;
import site.lmacedo.kiekishop.ordering.domain.model.commons.FullName;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import site.lmacedo.kiekishop.ordering.infrastructure.persistence.customer.CustomersPersistenceProvider;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
class CustomersIT {

    private Customers customers;

    @Autowired
    public CustomersIT(Customers customers) {
        this.customers = customers;
    }

    @Test
    void shouldPersistAndFind() {
        Customer originalCustomer = CustomerTestDataBuilder.brandNewCustomer().build();
        CustomerId customerId = originalCustomer.id();
        customers.add(originalCustomer);

        Optional<Customer> possibleCustomer = customers.ofId(customerId);

        Assertions.assertThat(possibleCustomer).isPresent();

        Customer savedCustomer = possibleCustomer.get();

        Assertions.assertThat(savedCustomer).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(customerId)
        );
    }

    @Test
    void shouldUpdateExistingCustomer() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        customer = customers.ofId(customer.id()).orElseThrow();
        customer.archive();

        customers.add(customer);

        Customer savedCustomer = customers.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(savedCustomer.archivedAt()).isNotNull();
        Assertions.assertThat(savedCustomer.isArchived()).isTrue();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Customer customerT1 = customers.ofId(customer.id()).orElseThrow();
        Customer customerT2 = customers.ofId(customer.id()).orElseThrow();

        customerT1.archive();
        customers.add(customerT1);

        customerT2.changeName(new FullName("Alex", "Silva"));

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> customers.add(customerT2));

        Customer savedCustomer = customers.ofId(customer.id()).orElseThrow();

        Assertions.assertThat(savedCustomer.archivedAt()).isNotNull();
        Assertions.assertThat(savedCustomer.isArchived()).isTrue();
        Assertions.assertThat(savedCustomer.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous"));
    }

    @Test
    void shouldCountExistingOrders() {
        Assertions.assertThat(customers.count()).isZero();

        Customer customer1 = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer1);

        Customer customer2 = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer2);

        Assertions.assertThat(customers.count()).isEqualTo(2L);
    }

    @Test
    void shouldReturnValidateIfOrderExists() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Assertions.assertThat(customers.exists(customer.id())).isTrue();
        Assertions.assertThat(customers.exists(new CustomerId())).isFalse();
    }

    @Test
    void shouldFindByEmail() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Optional<Customer> customerOptional = customers.ofEmail(customer.email());

        Assertions.assertThat(customerOptional).isPresent();
    }

    @Test
    void shouldNotFindByEmailIfNotCustomer() {
        Optional<Customer> customerOptional = customers.ofEmail(new Email(UUID.randomUUID() + "@gmail.com"));
        Assertions.assertThat(customerOptional).isNotPresent();
    }

    @Test
    void shouldReturnIfEmailIsInUse(){
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Assertions.assertThat(customers.isEmailUnique(customer.email(), customer.id())).isTrue();
        Assertions.assertThat(customers.isEmailUnique(customer.email(), new CustomerId())).isFalse();
        Assertions.assertThat(customers.isEmailUnique(new Email("lucas@gmail.com"), new CustomerId())).isTrue();
    }
}