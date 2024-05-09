package com.example.coursework.repository;

import com.example.coursework.model.Role;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    @Query("select r from Role r join r.users u where u.id = :userId")
    List<Role> findByUserId(@Param("userId") Long userId);
}
