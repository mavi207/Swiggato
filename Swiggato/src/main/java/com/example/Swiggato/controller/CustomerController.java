package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;  // field injection

    /*
    Constructor Injection ----> Always use in enterprise applications
     */
//    final CustomerServiceImpl customerService;
//
//    @Autowired
//    public CustomerController(CustomerServiceImpl customerService){
//        this.customerService = customerService;
//    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return new ResponseEntity(customerResponse , HttpStatus.CREATED);
    }

    @GetMapping("/find-by-mobile")
    public ResponseEntity findCustomerByMobile(@RequestParam String phoneNumber){
        try{
            CustomerResponse customerResponse = customerService.findCustomerByMobile(phoneNumber);
            return new ResponseEntity<>(customerResponse , HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FOUND);
        }
    }

}
