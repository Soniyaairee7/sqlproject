package com.bookstore.book.controller;

import com.bookstore.book.entity.Book;
import com.bookstore.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/get-all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public Book getBook(@PathVariable String id) {
        return bookService.getBook(id);
    }
    
    @PostMapping("/get-multiple/")
    public List<Book> getMultipleBooks(@RequestBody String ids) {
        return bookService.getMultipleBooks(ids);
    }
}
