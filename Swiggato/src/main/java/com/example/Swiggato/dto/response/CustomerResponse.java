package com.example.Swiggato.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CustomerResponse {

    String name;

    String mobileNo;

    String address;

    CartResponse cartResponse;

}
