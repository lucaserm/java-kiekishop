package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void shouldGenerateWithValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldGenerateWithoutValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        Assertions.assertThat(loyaltyPoints.value()).isZero();
    }

    @Test
    void shouldAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.add(15).value()).isEqualTo(25);
    }

    @Test
    void shouldNotAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> loyaltyPoints.add(-5));
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldNotAddZeroValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltyPoints.add(0));
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

}