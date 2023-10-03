package com.example.Swiggato.model;

import com.example.Swiggato.Enum.RestaurantCategory;
import com.example.Swiggato.Enum.RestaurantOpened;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PACKAGE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "location")
    String location;

    @Column(name = "address")
    String address;

    @Enumerated(value = EnumType.STRING)
    RestaurantCategory restaurantCategory;

    @Size(min = 10 , max = 10)
    @Column(unique = true, nullable = false)
    String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    RestaurantOpened restaurantOpened;

    @OneToMany
    @Column(name = "order")
    List<OrderEntity> orders = new ArrayList<>();

    @OneToMany
    @Column(name = "food_item")
    List<FoodItem> availableFoodItems = new ArrayList<>();
}
