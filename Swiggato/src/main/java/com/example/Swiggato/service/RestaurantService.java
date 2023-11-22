package com.example.Swiggato.service;

import com.example.Swiggato.Utils.ValidationUtils;
import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.exception.MenuItemNotFoundException;
import com.example.Swiggato.exception.RestaurantNotFoundException;
import com.example.Swiggato.exception.RestaurantNotOpendException;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.MenuItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    // by constructor injection (industry prefered)
    final RestaurantRepository restaurantRepository;
    final ValidationUtils validationUtils;
    final MenuRepository menuRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, ValidationUtils validationUtils, MenuRepository menuRepository){
        this.restaurantRepository=restaurantRepository;
        this.validationUtils = validationUtils;
        this.menuRepository = menuRepository;
    }

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        // requestDTO to model restaurant
        Restaurant restaurant = RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        //save in db
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        // prepare response dto and return
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public String changeStatus(int id) {
        // check restaurant is existed or not manually or just call validationUtil
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Restauarnt doesn't exist !!!");
        }
        Restaurant restaurant = restaurantOptional.get();
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRepository.save(restaurant);
        if(restaurant.isOpened()){
            // true ie opened
            return "Restaurant is Opened now !!!";
        }
        return "Restaurant is Closed !!!";
    }

    public RestaurantResponse addMenuItemToRestaurant(MenuRequest menuRequest) {

        // check restaurant is valid or not
        if(!validationUtils.validRestaurant(menuRequest.getRestaurantId())){
            throw new RestaurantNotFoundException("Restaurant does not exist !!!");
        }

        // if exist , get restaurant, no need to take optional now.., just get at last
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId()).get();
        // requestDTO to foodItem

        MenuItem menuItem = MenuItemTransformer.MenuRequestToMenuItem(menuRequest);
        menuItem.setRestaurant(restaurant);

        // now add menuItem in list of menuItem in restaurants
        restaurant.getAvailableMenuItems().add(menuItem);

        // save both restaurant and menuItem :- just save restaurant
        Restaurant savedRestaurant =  restaurantRepository.save(restaurant);

        // return response
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public List<MenuResponse> getMenu(int id) {
        List<MenuItem> menuItemList = menuRepository.findAll();
        List<MenuResponse> menuResponsesList = new ArrayList<>();
        for(MenuItem menuItem : menuItemList){
            if(menuItem.getRestaurant().getId()==id){
                menuResponsesList.add(MenuItemTransformer.MenuItemToMenuResponse(menuItem));
            }
        }

        return menuResponsesList;
    }

    public List<MenuResponse> getMenuItemsOfCategory(int restId, String category) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restId);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Invalid id !!!");
        }
        Restaurant restaurant = restaurantOptional.get();
        List<MenuResponse> menuResponseList = new ArrayList<>();
        for(MenuItem menuItem : restaurant.getAvailableMenuItems()){
            if(menuItem.getCategory().toString().equals(category)){
                menuResponseList.add(MenuItemTransformer.MenuItemToMenuResponse(menuItem));
            }
        }
        if(menuResponseList.isEmpty()){
            throw new MenuItemNotFoundException("Restaurant does not have this category foodItems !!!");
        }
        return menuResponseList;
    }

    public List<MenuResponse> getVegMenuItems(int restId) {
        // check it is valid or not by using util
        boolean b = validationUtils.validRestaurant(restId);
        if(!b){
            throw new RestaurantNotFoundException("Restaurant does not exist !!!");
        }
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        Restaurant restaurant  = optionalRestaurant.get();

        // also check restaurant is opend or closed
        if(!restaurant.isOpened()) throw new RestaurantNotOpendException("Restaurant is currently closed !!!");

        List<MenuResponse> list  = new ArrayList<>();
        for (MenuItem menuItem : restaurant.getAvailableMenuItems()){
            if(menuItem.isVeg()){
                list.add(MenuItemTransformer.MenuItemToMenuResponse(menuItem));
            }
        }
        if(list.size()==0) throw new MenuItemNotFoundException("Restaurant does not have veg menuItems !!!");

        return list;
    }
}