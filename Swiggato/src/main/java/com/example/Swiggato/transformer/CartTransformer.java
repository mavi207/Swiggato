package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.model.Cart;

import java.util.ArrayList;

public class CartTransformer {

    public static CartResponse CartToCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(new ArrayList<>())
                .build();
    }

    public static CartStatusResponse CartToCartStatusResponse(Cart savedCart){
        return CartStatusResponse.builder()
                .customerName(savedCart.getCustomer().getName())
                .customerMobile(savedCart.getCustomer().getMobileNo())
                .customerAddress(savedCart.getCustomer().getAddress())

                .build();
    }
}
