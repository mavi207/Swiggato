package com.example.Swiggato.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_entity")
public class OrderEntity {

    @Id
    int id;

    @Column(name = "order_id")
    String orderId;

    int totalOrder;

    @CreationTimestamp
    Date orderTime;

    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    DeliveryPartner deliveryPartner;

    @OneToMany(mappedBy = "" , cascade = CascadeType.ALL)
    List<FoodItem> foodItemList = new ArrayList<>();

    @ManyToOne
    Restaurant restaurant;


}
