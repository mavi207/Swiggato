package com.example.Swiggato.controller;

import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OrderController {

    final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/mobile/{mobile}")
    public ResponseEntity placeOrder(@PathVariable("mobile") String customerMobile){

        try{
            OrderResponse orderResponse = orderService.placeOrder(customerMobile);
            return new ResponseEntity(orderResponse,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}