package com.bookstore.order.repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookstore.order.entity.Order;



public interface OrderRepo extends MongoRepository<Order, String> {

}




