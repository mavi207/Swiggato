package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.dto.response.DeliveryPartnerResponse;
import com.example.Swiggato.model.DeliveryPartner;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.transformer.DeliveryPartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        // dto to model
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
        DeliveryPartner savedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
        return "You have been successfully regsitered!!!";
    }

    public DeliveryPartnerResponse DeliveryPartnerWithMostNoOfOrders() {
        List<DeliveryPartner> list = deliveryPartnerRepository.findAll();
        int maxSize = Integer.MIN_VALUE;
        DeliveryPartner deliveryPartner = null;
        for(DeliveryPartner p : list){
            int size = p.getOrders().size();
            if(size>maxSize){
                maxSize = Math.max(maxSize,size);
                deliveryPartner = p;
            }
        }
        DeliveryPartnerResponse deliveryPartnerResponse = DeliveryPartnerTransformer.DeliveryPartnerToDeliveryPartnerResponse(deliveryPartner);
        deliveryPartnerResponse.setTotalNoOfOrders(maxSize);
        return deliveryPartnerResponse;
    }
}
