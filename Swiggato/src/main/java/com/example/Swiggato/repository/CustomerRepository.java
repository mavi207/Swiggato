package com.example.Swiggato.repository;

import com.example.Swiggato.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByMobileNo(String customerMobile);
    // find customer by mobile no
    
}
