package com.example.coursework;

import com.example.coursework.model.*;
import com.example.coursework.repository.OrderItemRepository;
import com.example.coursework.repository.OrderRepository;
import com.example.coursework.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder() {
        // Создать элементы заказа
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setBook(new Book());
        orderItem1.setCountProduct(2);
        orderItem1.setTotalPrice(100.0);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setBook(new Book());
        orderItem2.setCountProduct(3);
        orderItem2.setTotalPrice(200.0);

        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);

        // Создать заказ
        Order order = new Order();
        order.setId(1L);
        order.setUser(new User());
        order.setOrderItem(orderItems);
        order.setTotalPrice(300.0);
        order.setCountProduct(5);

        // Настроить мок-объекты
        when(orderItemRepository.save(orderItem1)).thenReturn(orderItem1);
        when(orderItemRepository.save(orderItem2)).thenReturn(orderItem2);
        when(orderRepository.save(order)).thenReturn(order);

        // Вызвать метод createOrder
        Order createdOrder = orderService.createOrder(order);

        // Проверить, что метод вернул ожидаемый результат
        assertEquals(1L, createdOrder.getId().longValue());
        assertEquals(5, createdOrder.getCountProduct());
        assertEquals(300.0, createdOrder.getTotalPrice(), 0.0);
        assertEquals(2, createdOrder.getOrderItem().size());

        // Проверить, что методы мок-объектов были вызваны правильно
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCreateOrderFromShoppingCart() {
        // Создать элементы корзины
        ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem();
        shoppingCartItem1.setId(1L);
        shoppingCartItem1.setBook(new Book());
        shoppingCartItem1.setCountProduct(2);
        shoppingCartItem1.setTotalPrice(100.0);

        ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem();
        shoppingCartItem2.setId(2L);
        shoppingCartItem2.setBook(new Book());
        shoppingCartItem2.setCountProduct(3);
        shoppingCartItem2.setTotalPrice(200.0);

        List<ShoppingCartItem> shoppingCartItems = Arrays.asList(shoppingCartItem1, shoppingCartItem2);

        // Создать корзину
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(new User());
        shoppingCart.setShoppingCartItem(shoppingCartItems);
        shoppingCart.setTotalPrice(300.0);
        shoppingCart.setCountProduct(5);

        // Настроить мок-объекты
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Вызвать метод createOrderFromShoppingCart
        Order createdOrder = orderService.createOrderFromShoppingCart(shoppingCart);

        // Проверить, что метод вернул ожидаемый результат
        assertEquals(300.0, createdOrder.getTotalPrice(), 0.0);
        assertEquals(5, createdOrder.getCountProduct());
        assertEquals(2, createdOrder.getOrderItem().size());

        // Проверить, что методы мок-объектов были вызваны правильно
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));
    }
}
