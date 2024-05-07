package com.example.coursework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_price")
    private double totalPrice;

//    @ManyToMany
//    @JoinTable(
//            name = "order_books",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
//    private List<Book> books;

    // конструкторы, геттеры и сеттеры
}
