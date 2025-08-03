package site.lmacedo.kiekishop.ordering.domain.model.valueobject.id;

import io.hypersistence.tsid.TSID;
import site.lmacedo.kiekishop.ordering.domain.model.utility.IdGenerator;

import java.util.Objects;

public record OrderItemId(TSID value) {
    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId() {
        this(IdGenerator.generateTSID());
    }

    public OrderItemId(Long value){
        this(TSID.from(value));
    }

    public OrderItemId(String value){
        this(TSID.from(value));
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
