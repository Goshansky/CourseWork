package com.example.coursework.controller;

import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.User;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam("userName") String userName, Model model) {
//        // Получить текущего пользователя
//        User currentUser = userService.findByUserName(userName);
//
//        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Id(currentUser.getId()).orElse(null);
//
//        if (shoppingCart == null) {
//            // Создать новую корзину для текущего пользователя
//            shoppingCart = new ShoppingCart();
//            shoppingCart.setUser(currentUser);
//            shoppingCartRepository.save(shoppingCart);
//        }
//
//        // Добавить корзину и ее элементы в модель
//        model.addAttribute("shoppingCart", shoppingCart);
//        model.addAttribute("shoppingCartItems", shoppingCart.getShoppingCartItem());
//
//        // Вернуть имя страницы корзины
//        return "cart";
//    }
}
