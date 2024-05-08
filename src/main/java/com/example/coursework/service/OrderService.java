package com.example.coursework.service;

import com.example.coursework.model.Order;
import com.example.coursework.model.OrderItem;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.repository.OrderItemRepository;
import com.example.coursework.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(Order order) {
        // Сохранить элементы заказа
        List<OrderItem> orderItems = order.getOrderItem();
        for (OrderItem orderItem : orderItems) {
            orderItemRepository.save(orderItem);
        }

        // Добавить элементы заказа в заказ
        order.setOrderItem(orderItems);

        // Сохранить заказ
        return orderRepository.save(order);
    }

    @Transactional
    public Order createOrderFromShoppingCart(ShoppingCart shoppingCart) {
        // Создать новый заказ
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setCountProduct(shoppingCart.getCountProduct());

        // Создать список элементов заказа на основе элементов корзины
        List<OrderItem> orderItems = shoppingCart.getShoppingCartItem().stream()
                .map(shoppingCartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(shoppingCartItem.getBook());
                    orderItem.setCountProduct(shoppingCartItem.getCountProduct());
                    orderItem.setTotalPrice(shoppingCartItem.getTotalPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Сохранить элементы заказа в базе данных
        orderItems.forEach(orderItem -> orderItemRepository.save(orderItem));



        // Добавить элементы заказа в заказ
        order.setOrderItem(orderItems);

        // Вернуть новый заказ
        return order;
    }

}
