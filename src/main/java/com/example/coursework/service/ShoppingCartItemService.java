package com.example.coursework.service;

import com.example.coursework.model.Book;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.model.User;
import com.example.coursework.repository.BookRepository;
import com.example.coursework.repository.ShoppingCartItemRepository;
import com.example.coursework.repository.ShoppingCartRepository;
import com.example.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class ShoppingCartItemService {
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Transactional
    public ShoppingCartItem addShoppingCartItem(int bookId, int countProduct, Long userId) {
        // Найти книгу по идентификатору
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Книга не найдена"));

        // Найти пользователя по идентификатору
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Найти текущую корзину пользователя
        ShoppingCart shoppingCart = user.getShoppingCart();
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        if (shoppingCart == null) {
            // Если корзина не существует, создать новую
            shoppingCart = shoppingCartService.createShoppingCart(userId);

            // Если элемента корзины не существует, создать новый
            shoppingCartItem.setBook(book);
            shoppingCartItem.setCountProduct(countProduct);
            shoppingCartItem.setTotalPrice(book.getBookPrice() * countProduct);
            shoppingCartItem.setShoppingCart(shoppingCart);
            shoppingCartItemRepository.save(shoppingCartItem);
            // Добавить новый элемент корзины в список элементов корзины
            if (shoppingCart.getShoppingCartItem() == null) {
                shoppingCart.setShoppingCartItem(new ArrayList());
            }
            shoppingCart.getShoppingCartItem().add(shoppingCartItem);


            // Обновить стоимость и количество в корзине
            shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, shoppingCart.getShoppingCartItem());

            // Сохранить обновленную корзину
            shoppingCartRepository.save(shoppingCart);

            return shoppingCartItem;
        }
        else {
            // Проверить, существует ли уже элемент корзины с этой книгой
            ShoppingCartItem existingShoppingCartItem = shoppingCart.getShoppingCartItem().stream()
                    .filter(item -> item.getBook().getId() == book.getId())
                    .findFirst()
                    .orElse(null);

            if (existingShoppingCartItem != null) {
                // Если элемент корзины уже существует, обновить количество и стоимость
                existingShoppingCartItem.setCountProduct(existingShoppingCartItem.getCountProduct() + countProduct);
                existingShoppingCartItem.setTotalPrice(existingShoppingCartItem.getTotalPrice() + book.getBookPrice() * countProduct);
            } else {
                // Если элемента корзины не существует, создать новый
                shoppingCartItem.setBook(book);
                shoppingCartItem.setCountProduct(countProduct);
                shoppingCartItem.setTotalPrice(book.getBookPrice() * countProduct);
                shoppingCartItem.setShoppingCart(shoppingCart);
                shoppingCartItemRepository.save(shoppingCartItem);
                // Добавить новый элемент корзины в список элементов корзины
                shoppingCart.getShoppingCartItem().add(shoppingCartItem);
            }
            // Обновить стоимость и количество в корзине
            shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, shoppingCart.getShoppingCartItem());

            // Сохранить обновленную корзину
            shoppingCartRepository.save(shoppingCart);
            return existingShoppingCartItem;
        }
    }

    @Transactional
    public void deleteShoppingCartItem(Long userId, int bookId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Id(userId).orElseThrow(() -> new RuntimeException("Корзина не найдена"));
        try {
        ShoppingCartItem shoppingCartItem = shoppingCart.getShoppingCartItem().stream()
                .filter(item -> item.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow();

        // Уменьшить количество книг в элементе корзины на 1
        if (shoppingCartItem.getCountProduct() > 1) {
            shoppingCartItem.setCountProduct(shoppingCartItem.getCountProduct() - 1);
            shoppingCartItem.setTotalPrice(shoppingCartItem.getBook().getBookPrice() * shoppingCartItem.getCountProduct());
            shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, shoppingCart.getShoppingCartItem());
        } else if(shoppingCartItem.getCountProduct() == 1) {
            // Если количество книг в элементе корзины равно 1, удалить элемент корзины из базы данных
            shoppingCart.getShoppingCartItem().remove(shoppingCartItem);
            shoppingCartItemRepository.delete(shoppingCartItem);
            shoppingCartItem = new ShoppingCartItem();
        }
        } catch (NoSuchElementException e) {
            // Элемент корзины не найден
        }

    }
}
