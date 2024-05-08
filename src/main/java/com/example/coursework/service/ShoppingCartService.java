package com.example.coursework.service;

import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.model.User;
import com.example.coursework.repository.ShoppingCartItemRepository;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;


    public ShoppingCart createShoppingCart(Long userId) {
        // Найти пользователя по идентификатору
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Создать новую корзину
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setCountProduct(0);

        // Сохранить новую корзину
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Вернуть сохраненную корзину
        return savedShoppingCart;
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, List<ShoppingCartItem> shoppingCartItems) {
        // Обнулить стоимость и количество
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setCountProduct(0);

        // Обновить стоимость и количество на основе списка элементов корзины
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + shoppingCartItem.getTotalPrice());
            shoppingCart.setCountProduct(shoppingCart.getCountProduct() + shoppingCartItem.getCountProduct());
        }

        // Сохранить обновленную корзину
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        // Вернуть сохраненную корзину
        return savedShoppingCart;
    }



}
