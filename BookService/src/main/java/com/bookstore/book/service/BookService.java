package com.bookstore.book.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.book.entity.Book;
import com.bookstore.book.repository.BookRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo repo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Book addBook(Book book) {
       // book.setId(UUID.randomUUID().toString());
        try {
            // Uncomment and adjust the following line if you have a notification service like MQTT
            // mqtGateway.senToMqtt(book.getId(), "bookIdTopic");
        } catch (Exception ex) {
            logger.warn("Cannot send message to notification service");
        }
               return  repo.save(book);
    }

    public Book getBook(String id) {
        Optional<Book> book = repo.findById(id);
        return book.isPresent() ? book.get() : null;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public List<Book> getMultipleBooks(String bookIds) {
        return StreamSupport.stream(repo.findAllById(Arrays.asList(bookIds.split(","))).spliterator(), false)
                .collect(Collectors.toList());
    }
}
