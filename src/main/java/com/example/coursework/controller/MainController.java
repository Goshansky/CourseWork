package com.example.coursework.controller;

import com.example.coursework.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {
    @Autowired
    private BookService bookService;


    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    @GetMapping("/catalog")
    public String homePage(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "catalog";
    }


}
