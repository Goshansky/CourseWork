package com.example.coursework.controller;

import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.User;
import com.example.coursework.repository.ShoppingCartItemRepository;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.service.ShoppingCartService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @GetMapping
    public String showCart(Model model, Principal principal) {

        String userName = principal.getName();

        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);
        // Получить текущую корзину пользователя
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Id(user.getId()).orElseThrow(() -> new RuntimeException("Корзина не найдена"));

        // Добавить корзину и ее элементы в модель
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("shoppingCartItems", shoppingCart.getShoppingCartItem());

        // Вернуть имя страницы корзины
        return "cart";
    }

}
