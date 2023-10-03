package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.stereotype.Service;

import static com.example.Swiggato.transformer.RestaurantTransformer.RestaurantRequestToRestaurant;
import static com.example.Swiggato.transformer.RestaurantTransformer.RestaurantToRestaurantResponse;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = RestaurantRequestToRestaurant(restaurantRequest);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantToRestaurantResponse(savedRestaurant);
    }
}
