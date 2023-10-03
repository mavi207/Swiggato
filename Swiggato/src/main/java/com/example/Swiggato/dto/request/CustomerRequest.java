package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {

    String name;

    String emailId;

    String address;

    String phoneNumber;

    Gender gender;

}
