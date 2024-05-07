package com.example.coursework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true)
    private String userName;
    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String password;

//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;

    // геттеры и сеттеры
}
