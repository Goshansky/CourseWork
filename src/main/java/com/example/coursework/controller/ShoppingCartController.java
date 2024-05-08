package com.example.coursework.controller;

import com.example.coursework.model.Book;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.service.BookService;
import com.example.coursework.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {


}
