package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.MenuItemNotFoundException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.transformer.CartTransformer;
import com.example.Swiggato.transformer.FoodIteamTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    final CustomerRepository customerRepository;
    final MenuRepository menuRepository;
    final CartRepository cartRepository;
    final FoodRepository foodRepository;

    @Autowired
    public CartService(CustomerRepository customerRepository, MenuRepository menuRepository, CartRepository cartRepository, FoodRepository foodRepository) {
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
    }


    public CartStatusResponse addFoodItemToCart(FoodRequest foodRequest) {

        Customer customer = customerRepository.findByMobileNo(foodRequest.getCustomerMobile());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exisit");
        }

        Optional<MenuItem> menuItemOptional = menuRepository.findById(foodRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }

        MenuItem menuItem = menuItemOptional.get(); // we want to add in cart
        if(!menuItem.getRestaurant().isOpened() || !menuItem.isAvailable()){
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }

        Cart cart = customer.getCart();
        // if we want to add new food Iteam from new restaurant, then we have to clear the cart first ie dlt all existing iteams
        // so we will check weather the restaurant of new iteam and existing food iteam in cart are matching or not
        // also check cart mai iteam hai ki nahi
        if(cart.getFoodItems().size()!=0) {
            Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();
            if (!currRestaurant.equals(newRestaurant)) {
                // clear the cart
                List<FoodItem> foodItemList = cart.getFoodItems();
                foodRepository.deleteAll(foodItemList);
                cart.setCartTotal(0);
                cart.setFoodItems(new ArrayList<>());
            }
        }

        // before adding the fooditem to the cart, we will check if that food item is already present in the cart or not
        // if it already there in a cart , we will just increase the quantity

        Boolean alreadyExist = false;
        FoodItem savedFoodItem = new FoodItem();

        if(cart.getFoodItems().size()!=0){
            // traverse over fooditem list and check
            for (FoodItem foodItem : cart.getFoodItems()){
                if(foodItem.getMenuItem().getId()==menuItem.getId()){
                    // already present..
                    int currQuantity = foodItem.getRequiredQuantity(); // already presnt wali quantity
                    foodItem.setRequiredQuantity(currQuantity + foodRequest.getRequiredQuantity());
                    savedFoodItem = foodItem;
                    alreadyExist=true;
                    break;
                }
            }
        }

        // if iteam is new to cart ie not already presnt, then add it
        if(!alreadyExist){
            FoodItem foodItem = FoodItem.builder()
                    .menuItem(menuItem)
                    .requiredQuantity(foodRequest.getRequiredQuantity())
                    .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                    .build();
            savedFoodItem = foodRepository.save(foodItem);
            cart.getFoodItems().add(savedFoodItem); // save new foodIteam in cart ...
            menuItem.getFoodItems().add(savedFoodItem);
            savedFoodItem.setCart(cart);
        }

        // do calculations of bill
        double cartTotal = 0;

        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }

        cart.setCartTotal(cartTotal);

        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){

            FoodResponse foodResponse = FoodIteamTransformer.FoodIteamToFoodResponse(food);

            foodResponseList.add(foodResponse);
        }
        CartStatusResponse cartStatusResponse = CartTransformer.CartToCartStatusResponse(savedCart);

        cartStatusResponse.setFoodList(foodResponseList);
        cartStatusResponse.setRestaurantName(savedMenuItem.getRestaurant().getName());
        cartStatusResponse.setCartTotal(cartTotal);
        return cartStatusResponse;

    }
}
