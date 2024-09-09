package com.soniya.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soniya.book.entity.Book;
import com.soniya.book.service.BookServiceImpl;

@RestController
@RequestMapping("/book")
public class BookController {

    public BookController() {
        System.out.println("Creating bean of Book controller");
    }

    @Autowired
    private BookServiceImpl bookService;

    @PostMapping("/")
    public Book addBook(@RequestBody Book book) {
        System.out.println(book);
        return bookService.addBook(book);
    }

    @GetMapping("/get-all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
