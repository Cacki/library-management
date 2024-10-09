package com.librarymanagement.controller;

import com.librarymanagement.entity.Book;
import com.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @Autowired
    public BookController(BookService bookService) {
        this.service = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return service.addBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

}
