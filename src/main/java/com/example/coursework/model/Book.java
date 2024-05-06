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
    private String bookPrice;
    @Column(length = 1000)
    private String description;
    //@OneToMany(mappedBy = "book")
    //private List<OrderItem> orderItem;
}
