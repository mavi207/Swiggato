package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.model.Customer;

public class CustomerTransformer {
    public static Customer CustomerRequestToCustomer (CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .emailId(customerRequest.getEmailId())
                .address(customerRequest.getAddress())
                .phoneNumber(customerRequest.getPhoneNumber())
                .gender(customerRequest.getGender())
                .build();
    }

    public static CustomerResponse CustomerToCustomerResponse (Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .emailId(customer.getEmailId())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .gender(customer.getGender())
                .build();
    }

}
