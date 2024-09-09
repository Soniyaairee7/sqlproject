package com.bookstore.book_customer.controller;

import com.bookstore.book_customer.entity.Customer;
import com.bookstore.book_customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("/get-multiple/")
    public List<Customer> getMultipleCustomers(@RequestBody String ids) {
        return customerService.getMultipleCustomers(ids);
    }
}
