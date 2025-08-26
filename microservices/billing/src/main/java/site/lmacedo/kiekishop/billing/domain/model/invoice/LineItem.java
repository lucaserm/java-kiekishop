package site.lmacedo.kiekishop.billing.domain.model.invoice;

import jakarta.persistence.Embeddable;
import lombok.*;
import site.lmacedo.kiekishop.billing.domain.model.FieldValidations;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LineItem {
    private Integer number;
    private String name;
    private BigDecimal amount;

    @Builder
    public LineItem(Integer number, String name, BigDecimal amount) {
        FieldValidations.requiresNonBlank(name);
        Objects.requireNonNull(number);
        Objects.requireNonNull(amount);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }

        if (number <= 0) {
            throw new IllegalArgumentException();
        }

        this.number = number;
        this.name = name;
        this.amount = amount;
    }
}
