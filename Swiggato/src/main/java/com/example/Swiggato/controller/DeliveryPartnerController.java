package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.dto.response.DeliveryPartnerResponse;
import com.example.Swiggato.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class DeliveryPartnerController {

    final DeliveryPartnerService deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){
        String message = deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
        return new ResponseEntity(message, HttpStatus.CREATED);
    }


    // give delivery partner with highest number of deliveries
    @GetMapping("/Delivery_part_With_Most_No_Of_Orders")
    public DeliveryPartnerResponse DeliveryPartnerWithMostNoOfOrders(){
        return deliveryPartnerService.DeliveryPartnerWithMostNoOfOrders();
    }

    // send an email to all the partners who have done less than 10 deliveries.
}