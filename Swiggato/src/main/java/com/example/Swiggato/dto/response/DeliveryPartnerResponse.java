package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeliveryPartnerResponse {
    int id;

    String name;

    String mobileNo;

    Gender gender;

    int TotalNoOfOrders;
}
