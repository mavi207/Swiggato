package com.example.Swiggato.service;

import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.EmptyCartException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.repository.OrderRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    JavaMailSender javaMailSender;
    final CustomerRepository customerRepository;
    final OrderRepository orderRepository;

    final DeliveryPartnerRepository deliveryPartnerRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository, DeliveryPartnerRepository deliveryPartnerRepository, RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public OrderResponse placeOrder(String customerMobile) {
        // verify the customer
        Customer customer = customerRepository.findByMobileNo(customerMobile);
        if(customer == null){
            throw new CustomerNotFoundException("Invalid mobile number!!!");
        }

        // verify if cart is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry! Your cart is empty!!!");
        }

        // find a delivery partner to deliver. Randomly
        DeliveryPartner partner = deliveryPartnerRepository.findRandomDeliveryPartner();
        // just get restaurant from any foodItems from list
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();

        // prepare the order entity
        OrderEntity order = OrderTransformer.prepareOrderEntity(cart);

        OrderEntity savedOrder = orderRepository.save(order);

        order.setCustomer(customer);
        order.setDeliveryPartner(partner);
        order.setRestaurant(restaurant);
        order.setFoodItems(cart.getFoodItems());

        customer.getOrders().add(savedOrder);
        partner.getOrders().add(savedOrder);
        restaurant.getOrders().add(savedOrder);

        // due to bi-directional mapping we have to do these extra stuff. In actual project or in industry we will
        // do all things only by unidirectional mapping

        for(FoodItem foodItem: cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrder);
        }

        clearCart(cart);

        customerRepository.save(customer);
        deliveryPartnerRepository.save(partner);
        restaurantRepository.save(restaurant);

        // send Mail:-
        // also we customer will get mail regarding his order ie ur food is on the way ....
        // by using SMTP

        // now we are also sending real life mail to user's mail

        String text = "Hi! " + customer.getName() + "\nYou have succefully placed your order !!!" +
                "\n\nOrder Details :"+"\norder id : "+savedOrder.getOrderId()+
                "\norder Total : "+savedOrder.getOrderTotal()+
                "\norder Time : "+savedOrder.getOrderTime()+
                "\nRestaurant : "+savedOrder.getRestaurant()+
                "\n\nDelivery Partner :"+"\nName : "+partner.getName()+
                "\nMob No : "+partner.getMobileNo()+
                "\n\nYour Order is on the way !!!"+
                "\n\nKeep Ordering !!! Enjoy your food !!!!";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); // default object
        simpleMailMessage.setFrom("arpitspringboot@gmail.com"); //  mail from which we want to send
        simpleMailMessage.setTo(customer.getEmail()); // mail of user
        simpleMailMessage.setSubject("Congrats!! You have succefully placed your order !!!"); // subject to the mail
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);


        // prepare orderresponse and return
        return OrderTransformer.OrderToOrderResponse(savedOrder);


    }
    private void clearCart(Cart cart) {
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
