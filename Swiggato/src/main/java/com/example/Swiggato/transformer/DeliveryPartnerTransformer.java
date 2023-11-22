package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.dto.response.DeliveryPartnerResponse;
import com.example.Swiggato.model.DeliveryPartner;

import java.util.ArrayList;

public class DeliveryPartnerTransformer {

    public static DeliveryPartner DeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest){
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .mobileNo(deliveryPartnerRequest.getMobileNo())
                .gender(deliveryPartnerRequest.getGender())
                .orders(new ArrayList<>())
                .build();
    }

    public static DeliveryPartnerResponse DeliveryPartnerToDeliveryPartnerResponse(DeliveryPartner deliveryPartner){
        return DeliveryPartnerResponse.builder()
                .id(deliveryPartner.getId())
                .gender(deliveryPartner.getGender())
                .name(deliveryPartner.getName())
                .mobileNo(deliveryPartner.getMobileNo())
                .build();
    }
}
