package com.example.Swiggato.model;

import com.example.Swiggato.Enum.FoodCategory;
import com.example.Swiggato.Enum.IsAvailable;
import com.example.Swiggato.Enum.IsVeg;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "food_item")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String dishName;

    double price;

    @Enumerated(EnumType.STRING)
    FoodCategory category;

    IsVeg veg;

    IsAvailable available;

    @ManyToOne
    @JoinColumn
    Cart cart;

    @ManyToOne
    @JoinColumn
    OrderEntity order;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

}
