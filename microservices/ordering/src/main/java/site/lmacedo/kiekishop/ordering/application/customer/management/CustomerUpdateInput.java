package site.lmacedo.kiekishop.ordering.application.customer.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.lmacedo.kiekishop.ordering.application.commons.AddressData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateInput {
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;
}