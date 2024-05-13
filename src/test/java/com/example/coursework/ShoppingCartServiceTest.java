package com.example.coursework;

import com.example.coursework.model.Book;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.model.User;
import com.example.coursework.repository.ShoppingCartItemRepository;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.repository.UserRepository;
import com.example.coursework.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    public void testUpdateShoppingCart() {
        // Создать элементы корзины
        ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem();
        shoppingCartItem1.setId(1L);
        shoppingCartItem1.setBook(new Book());
        shoppingCartItem1.setCountProduct(2);
        shoppingCartItem1.setTotalPrice(20.0);

        ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem();
        shoppingCartItem2.setId(2L);
        shoppingCartItem2.setBook(new Book());
        shoppingCartItem2.setCountProduct(3);
        shoppingCartItem2.setTotalPrice(30.0);

        List<ShoppingCartItem> shoppingCartItems = Arrays.asList(shoppingCartItem1, shoppingCartItem2);

        // Создать корзину
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(new User());
        shoppingCart.setShoppingCartItem(shoppingCartItems);

        // Настроить мок-объект для сохранения корзины
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);

        // Вызвать метод updateShoppingCart
        ShoppingCart updatedShoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, shoppingCartItems);

        // Проверить, что метод вернул ожидаемый результат
        assertNotNull(updatedShoppingCart);
        assertEquals(50.0, updatedShoppingCart.getTotalPrice(), 0.0);
        assertEquals(5, updatedShoppingCart.getCountProduct());

        // Проверить, что метод мок-объекта был вызван правильно
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }
}
