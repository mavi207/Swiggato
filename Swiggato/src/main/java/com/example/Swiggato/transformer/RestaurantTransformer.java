package com.example.Swiggato.transformer;

import com.example.Swiggato.Enum.RestaurantOpened;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;

import java.util.ArrayList;

public class RestaurantTransformer {
    public static Restaurant RestaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
                .name(restaurantRequest.getName())
                .location(restaurantRequest.getLocation())
                .address(restaurantRequest.getAddress())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .phoneNumber(restaurantRequest.getPhoneNumber())
                .restaurantOpened(RestaurantOpened.Open)
                .orders(new ArrayList<>())
                .availableFoodItems(new ArrayList<>())
                .build();
    }

    public static RestaurantResponse RestaurantToRestaurantResponse(Restaurant restaurant){
        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .address(restaurant.getAddress())
                .phoneNumber(restaurant.getPhoneNumber())
                .restaurantCategory(restaurant.getRestaurantCategory())
                .build();
    }

}
