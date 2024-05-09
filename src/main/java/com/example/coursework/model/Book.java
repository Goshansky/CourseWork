package com.example.coursework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItem;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> shoppingCartItem;
    @ManyToOne
    private User user;

}
