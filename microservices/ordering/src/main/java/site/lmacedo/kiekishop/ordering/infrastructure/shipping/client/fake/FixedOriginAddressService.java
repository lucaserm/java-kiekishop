package site.lmacedo.kiekishop.ordering.infrastructure.shipping.client.fake;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.domain.model.commons.Address;
import site.lmacedo.kiekishop.ordering.domain.model.commons.ZipCode;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.OriginAddressService;

@Component
public class FixedOriginAddressService implements OriginAddressService {

    @Override
    public Address originAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .build();
    }
}