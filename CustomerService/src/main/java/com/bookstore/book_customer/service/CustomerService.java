package com.bookstore.book_customer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.book_customer.entity.Customer;
import com.bookstore.book_customer.repo.CustomerRepo;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo repo;

    Logger logger = LoggerFactory.getLogger(getClass());

    public Customer addCustomer(Customer customer) {
       // customer.setId(UUID.randomUUID().toString());
        try {
            // Assuming there is some notification or logging service similar to MQTT in UserServiceImpl
            // mqtGateway.senToMqtt(customer.getId(), "customerIdTopic");
        } catch (Exception ex) {
            logger.warn("Cannot send message to notification service");
        }
        
        return repo.save(customer);
    }

    public Customer getCustomer(String id) {
        Optional<Customer> customer = repo.findById(id);
        return customer.isPresent() ? customer.get() : null;
    }

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public List<Customer> getMultipleCustomers(String customerIds) {
        return repo.findAllById(Arrays.asList(customerIds.split(",")));
    }
}
