package com.soniya.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soniya.book.entity.Customer;
import com.soniya.book.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    public CustomerController() {
        System.out.println("Creating bean of Customer controller");
    }

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/")
    public Customer addCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        return customerService.addCustomer(customer);
    }

    @GetMapping("/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
