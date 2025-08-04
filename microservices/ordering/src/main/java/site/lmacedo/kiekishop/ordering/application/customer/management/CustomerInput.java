package site.lmacedo.kiekishop.ordering.application.customer.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.lmacedo.kiekishop.ordering.application.commons.AddressData;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInput {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String document;
    private LocalDate birthDate;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;
}