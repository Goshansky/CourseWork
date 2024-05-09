package com.example.coursework.service;

import com.example.coursework.model.Book;
import com.example.coursework.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private BookRepository bookRepository;

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
