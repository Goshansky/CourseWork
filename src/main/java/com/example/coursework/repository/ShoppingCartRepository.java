package com.example.coursework.repository;

import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser_Id(Long userId);
}
