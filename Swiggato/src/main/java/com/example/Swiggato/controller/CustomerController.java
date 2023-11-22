package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    // Field Injection:-- not prefered in coroporate
    @Autowired
    CustomerService customerService;

//    // constructor injection:-- always use this method in company
//    @Autowired
//    public CustomerController(CustomerService customerService){
//        this.customerService=customerService;
//    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.CREATED);

    }
    @GetMapping("/get/mobile/{mobile}")
    public ResponseEntity getCustomerByMob(@PathVariable("mobile") String mobile){
        try {
            CustomerResponse customerResponse = customerService.getCustomerByMob(mobile);
            return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}