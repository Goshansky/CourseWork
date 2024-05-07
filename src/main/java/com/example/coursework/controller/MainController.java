package com.example.coursework.controller;

import com.example.coursework.model.User;
import com.example.coursework.service.BookService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    @GetMapping("/catalog")
    public String homePage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "catalog";
    }

    @GetMapping("/account")
    public String AccountPage(Model model, Principal principal) {
        // Получаем имя текущего пользователя из объекта Principal
        String userName = principal.getName();

        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);

        // Добавляем объект пользователя в модель для отображения на странице
        model.addAttribute("user", user);

        return "account";
    }


}
