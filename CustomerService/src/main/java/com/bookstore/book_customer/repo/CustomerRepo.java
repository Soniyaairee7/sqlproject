package com.bookstore.book_customer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.book_customer.entity.Customer;

public interface CustomerRepo extends MongoRepository<Customer, String> {

}
