package site.lmacedo.kiekishop.ordering.application.checkout;

import org.springframework.stereotype.Component;
import site.lmacedo.kiekishop.ordering.application.commons.AddressData;
import site.lmacedo.kiekishop.ordering.domain.model.commons.*;
import site.lmacedo.kiekishop.ordering.domain.model.order.Recipient;
import site.lmacedo.kiekishop.ordering.domain.model.order.Shipping;
import site.lmacedo.kiekishop.ordering.domain.model.order.shipping.ShippingCostService;

@Component
class ShippingInputDisassembler {

    public Shipping toDomainModel(ShippingInput shippingInput,
                                  ShippingCostService.CalculationResult shippingCalculationResult) {
        AddressData address = shippingInput.getAddress();
        return Shipping.builder()
                .cost(shippingCalculationResult.cost())
                .expectedDate(shippingCalculationResult.expectedDate())
                .recipient(Recipient.builder()
                        .fullName(new FullName(
                                shippingInput.getRecipient().getFirstName(),
                                shippingInput.getRecipient().getLastName()))
                        .document(new Document(shippingInput.getRecipient().getDocument()))
                        .phone(new Phone(shippingInput.getRecipient().getPhone()))
                        .build())
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}