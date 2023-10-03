package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.service.impl.RestaurantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final RestaurantServiceImpl restaurantService;

    /**
     * Constructor Injection
     * @param restaurantService  --> bean of restaurant Service
     */

    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
            RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
            return new ResponseEntity(restaurantResponse, HttpStatus.CREATED);
    }
}
