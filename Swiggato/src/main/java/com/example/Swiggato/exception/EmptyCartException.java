package com.example.Swiggato.exception;

public class  EmptyCartException extends RuntimeException{
    public EmptyCartException(String message){
        super(message);
    }
}