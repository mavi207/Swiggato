package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.RestaurantCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequest {
    String name;

    String location;

    String address;

    RestaurantCategory restaurantCategory;

    String phoneNumber;

}
