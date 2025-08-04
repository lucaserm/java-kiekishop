package site.lmacedo.kiekishop.ordering.application.utility;

public interface Mapper {
    <T> T convert(Object object, Class<T> destinationType);
}
