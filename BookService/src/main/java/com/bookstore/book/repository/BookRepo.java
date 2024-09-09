package com.bookstore.book.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.book.entity.Book;

public interface BookRepo extends MongoRepository<Book, String> {
}
