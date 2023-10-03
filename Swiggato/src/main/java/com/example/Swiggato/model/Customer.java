package com.example.Swiggato.model;

import com.example.Swiggato.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id //this gives that this is a primary key of table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Size(min = 4 , message = "{validation.name.size.too_short}")
    @Size(max = 40 , message = "validation.name.size.too_long")
    @Column(name = "name")
    String name;

    @Email
    @Column(name = "email_id" , unique = true , nullable = false)
    String emailId;

    @Column(name = "address")
    String address;

    @Column(name = "phone_number" , unique = true , nullable = false)
    @Size(max = 10 , min = 10)
    String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    Gender gender;

    @OneToOne(mappedBy = "customer" , cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
    List<OrderEntity> orderEntityList = new ArrayList<>();
}
