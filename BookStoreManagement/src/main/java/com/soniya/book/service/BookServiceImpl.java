package com.soniya.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soniya.book.entity.Book;

@Service
public class BookServiceImpl {

    List<Book> bookList = new ArrayList<>();
    int i = 1;

    public Book addBook(Book book) {
        book.setId(i);
        i++;
        bookList.add(book);
        return book;
    }

    public Book getBook(int id) {
        for (Book b : bookList) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return bookList;
    }
}
