package com.example.coursework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    private int id;
    private String name;
    private int bookPrice;
    @Column(length = 1000)
    private String description;

    @OneToOne
    private OrderItem orderItem;
    @OneToOne
    private ShoppingCartItem shoppingCartItem;

}
