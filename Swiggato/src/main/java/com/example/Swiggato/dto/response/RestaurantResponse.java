package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RestaurantResponse {

    String name;

    String location;

    String address;

    String phoneNumber;

    RestaurantCategory restaurantCategory;

}
