package com.example.coursework;

import com.example.coursework.model.Book;
import com.example.coursework.repository.BookRepository;
import com.example.coursework.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAll() {
        // Создать список книг
        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", 10, "Description1"),
                new Book(2, "Book 2", 20, "Description2"),
                new Book(3, "Book 3", 30, "Description3")
        );

        // Настроить мок-объект для возврата списка книг
        when(bookRepository.findAll()).thenReturn(books);

        // Вызвать метод findAll
        Iterable<Book> actualBooks = bookService.findAll();

        // Проверить, что метод вернул ожидаемый результат
        assertIterableEquals(books, actualBooks);

        // Проверить, что метод мок-объекта был вызван правильно
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Создать книгу
        Book book = new Book(1, "Book 1", 10, "Description1");

        // Настроить мок-объект для возврата книги
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Вызвать метод findById
        Book actualBook = bookService.findById(1);

        // Проверить, что метод вернул ожидаемый результат
        assertEquals(book, actualBook);

        // Проверить, что метод мок-объекта был вызван правильно
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Создать книгу
        Book book = new Book(1, "Book 1", 10, "Description1");

        // Настроить мок-объект для сохранения книги
        when(bookRepository.save(book)).thenReturn(book);

        // Вызвать метод save
        Book savedBook = bookService.save(book);

        // Проверить, что метод вернул ожидаемый результат
        assertEquals(book, savedBook);

        // Проверить, что метод мок-объекта был вызван правильно
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteById() {
        // Вызвать метод deleteById
        bookService.deleteById(1);

        // Проверить, что метод мок-объекта был вызван правильно
        verify(bookRepository, times(1)).deleteById(1);
    }
}
