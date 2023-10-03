package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CustomerResponse {

    String name;

    String emailId;

    String address;

    String phoneNumber;

    Gender gender;

}
