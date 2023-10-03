package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse addCustomer(CustomerRequest customerRequest);

    CustomerResponse findCustomerByMobile(String phoneNumber);
}
