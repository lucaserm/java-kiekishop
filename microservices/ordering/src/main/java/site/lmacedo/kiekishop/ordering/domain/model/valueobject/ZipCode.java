package site.lmacedo.kiekishop.ordering.domain.model.valueobject;

import java.util.Objects;

public record ZipCode(String value) {
    public ZipCode {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException();
        }
        int americanZipCode = 5;
        int brazilianZipCode = 8;
        if (value.length() != americanZipCode && value.length() != brazilianZipCode) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
