package com.example.coursework.controller;

import com.example.coursework.model.Order;
import com.example.coursework.model.Role;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.User;
import com.example.coursework.repository.OrderRepository;
import com.example.coursework.service.BookService;
import com.example.coursework.service.ShoppingCartService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    @GetMapping("/catalog")
    public String homePage(Model model, Principal principal) {
        String userName = principal.getName();

        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);

        Role role = user.getRoles().stream().findFirst().orElse(null);
        if (role != null) {
            String roleName = role.getName();
            System.out.println("Role name: " + roleName);
        }

        model.addAttribute("user", user);
        model.addAttribute("books", bookService.findAll());
        return "catalog";
    }

    @GetMapping("/account")
    public String AccountPage(Model model, Principal principal) {
        // Получаем имя текущего пользователя из объекта Principal
        String userName = principal.getName();

        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);
        List<Order> orders = orderRepository.findByUser_Id(user.getId());

        // Добавить список заказов в модель
        model.addAttribute("orders", orders);

        // Добавляем объект пользователя в модель для отображения на странице
        model.addAttribute("user", user);

        return "account";
    }

//    @GetMapping("/shopping-cart")
//    public String showShoppingCart(Model model, Principal principal) {
//        String username = principal.getName();
//        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUserName(username);
//        model.addAttribute("shoppingCart", shoppingCart);
//        return "shopping-cart";
//    }

}
