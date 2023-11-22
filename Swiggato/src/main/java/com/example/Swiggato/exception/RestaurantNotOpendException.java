package com.example.Swiggato.exception;

public class RestaurantNotOpendException extends RuntimeException{
    public RestaurantNotOpendException(String message){
        super(message);
    }
}