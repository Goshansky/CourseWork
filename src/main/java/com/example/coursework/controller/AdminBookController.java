package com.example.coursework.controller;

import com.example.coursework.model.Book;
import com.example.coursework.model.Role;
import com.example.coursework.model.User;
import com.example.coursework.service.BookService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/book")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showBook(Principal principal) {
        String userName = principal.getName();

        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);

        Role role = user.getRoles().stream().findFirst().orElse(null);
        if (role != null) {
            String roleName = role.getName();
            System.out.println("Role name: " + roleName);
        }

        return "book";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String name,
                          @RequestParam int price,
                          @RequestParam String description) {
        Book book = new Book();
        book.setName(name);
        book.setBookPrice(price);
        book.setDescription(description);
        bookService.save(book);
        return "redirect:/catalog";
    }

}
