package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        // DTO--> model
        Customer customer = CustomerTransformer.CustomerRequestToCustomer(customerRequest);

        // allcoacte the cart to registered customer
        // create cart
        Cart cart  = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();
        // set cart to customer
        customer.setCart(cart);
        // save both, :- save parent customer it will save child cart also
        Customer savedCustomer = customerRepository.save(customer);

        //prepare response dto
        CustomerResponse customerResponse  = CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
        return  customerResponse;

    }

    public CustomerResponse getCustomerByMob(String mobile) {
        // find by mob
        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid mobile no !!!!");
        }
        // model to dto
        return CustomerTransformer.CustomerToCustomerResponse(customer);

    }
}