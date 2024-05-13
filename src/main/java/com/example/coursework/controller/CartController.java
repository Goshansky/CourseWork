package com.example.coursework.controller;

import com.example.coursework.model.*;
import com.example.coursework.repository.OrderItemRepository;
import com.example.coursework.repository.OrderRepository;
import com.example.coursework.repository.ShoppingCartItemRepository;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.service.OrderService;
import com.example.coursework.service.ShoppingCartService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;

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

    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        // Получить текущую корзину пользователя
        String userName = principal.getName();
        // Получаем объект пользователя из базы данных по его имени
        User user = userService.findByUserName(userName);
        // Получить текущую корзину пользователя
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Id(user.getId()).orElseThrow(() -> new RuntimeException("Корзина не найдена"));
        // Создать новый заказ на основе текущей корзины
        Order order = orderService.createOrderFromShoppingCart(shoppingCart);
        // Сохранить новый заказ в базе данных
        orderRepository.save(order);
        // Очистить корзину
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setCountProduct(0);
        shoppingCart.getShoppingCartItem().clear();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemRepository.findByShoppingCart_Id(shoppingCart.getId());
        // Удалить все элементы корзины из базы данных
        shoppingCartItemRepository.deleteAll(shoppingCartItems);
        // Сохранить очищенную корзину в базе данных
        shoppingCartRepository.save(shoppingCart);
        // Вернуть имя страницы успешного оформления заказа
        return "redirect:/account";
    }

}
