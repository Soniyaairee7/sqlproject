package com.bookstore.order.repo.external;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.order.entity.external.Customer;

public interface CustomerRepo extends MongoRepository<Customer, String> {

}
