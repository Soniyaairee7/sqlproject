package com.soniya.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soniya.book.entity.Customer;

@Service
public class CustomerServiceImpl {

    List<Customer> customerList = new ArrayList<>();
    int i = 1;

    public Customer addCustomer(Customer customer) {
        customer.setId(i);
        i++;
        customerList.add(customer);
        return customer;
    }

    public Customer getCustomer(int id) {
        for (Customer c : customerList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        return customerList;
    }
}
