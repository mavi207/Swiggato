package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.service.CustomerService;
import org.springframework.stereotype.Service;

import static com.example.Swiggato.transformer.CustomerTransformer.CustomerRequestToCustomer;
import static com.example.Swiggato.transformer.CustomerTransformer.CustomerToCustomerResponse;
@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository; // defining of beam

    public CustomerServiceImpl(CustomerRepository customerRepository) { // initialisation of beam
        this.customerRepository = customerRepository;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = CustomerRequestToCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerToCustomerResponse(customer);
    }

    public CustomerResponse findCustomerByMobile(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if(customer==null){
            throw new CustomerNotFoundException("Phone number is Invalid!!");
        }
        return CustomerToCustomerResponse(customer);
    }
}
