package site.lmacedo.kiekishop.ordering.application.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.lmacedo.kiekishop.ordering.application.commons.AddressData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingInput {
    private RecipientData recipient;
    private AddressData address;
}