package com.bookstore.order.repo.external;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.order.entity.external.Book;

public interface BookRepo extends MongoRepository<Book, String> {

}
