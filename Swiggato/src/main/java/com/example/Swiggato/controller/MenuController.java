package com.example.Swiggato.controller;

import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    final RestaurantService restaurantService;

    @Autowired
    public MenuController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    // API's of all filters over the menu....

    // show all foodItem of restaurant
    @GetMapping("/getMenu")
    public ResponseEntity getMenu(@RequestParam int restId){
        List<MenuResponse> list = restaurantService.getMenu(restId);

        if(!list.isEmpty()) return new ResponseEntity(list, HttpStatus.FOUND);

        return new ResponseEntity("Invalid id !!!", HttpStatus.NOT_FOUND);

    }

    // show all foodItems of specific category
    @GetMapping("/getMenuItemsOfCategory")
    public ResponseEntity getMenuItemsOfCategory(@RequestParam int restId, @RequestParam String category){
        try{
            List<MenuResponse> list = restaurantService.getMenuItemsOfCategory(restId, category);
            return new ResponseEntity(list, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // get all veg menu from restaurant
    @GetMapping("/getVegMenuItems/restId/{restId}")
    public ResponseEntity getVegMenuItems(@PathVariable("restId") int restId){
        try{
            List<MenuResponse> list = restaurantService.getVegMenuItems(restId);
            return new ResponseEntity(list,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}