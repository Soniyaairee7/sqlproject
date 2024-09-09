package com.soniya.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soniya.book.entity.Order;
import com.soniya.book.entity.OrderDTO;
import com.soniya.book.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

    public OrderController() {
        System.out.println("Creating bean of Order controller");
    }

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/")
    public Order addOrder(@RequestBody Order order) {
        System.out.println(order);
        return orderService.addOrder(order);
    }

    @GetMapping("/get-all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
