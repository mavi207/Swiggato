package com.example.Swiggato.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message){
        super(message);
    }
}