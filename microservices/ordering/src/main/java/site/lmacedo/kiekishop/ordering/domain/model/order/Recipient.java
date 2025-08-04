package site.lmacedo.kiekishop.ordering.domain.model.order;

import lombok.Builder;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Document;
import site.lmacedo.kiekishop.ordering.domain.model.commons.FullName;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Phone;

import java.util.Objects;

@Builder
public record Recipient(FullName fullName, Document document, Phone phone) {
    public Recipient {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
    }
}
