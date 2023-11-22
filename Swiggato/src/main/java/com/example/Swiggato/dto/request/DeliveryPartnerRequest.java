package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartnerRequest {
    String name;

    String mobileNo;

    Gender gender;
}