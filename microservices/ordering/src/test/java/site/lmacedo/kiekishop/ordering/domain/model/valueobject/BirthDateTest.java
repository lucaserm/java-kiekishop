package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import site.lmacedo.kiekishop.ordering.domain.model.customer.BirthDate;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    void shouldShowAge() {
        BirthDate birthDate = new BirthDate(LocalDate.of(2005,3,22));
        Assertions.assertThat(birthDate.age()).isEqualTo(20);
    }

    @Test
    void shouldNotCreateBirthDate(){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(tomorrow));
    }

}