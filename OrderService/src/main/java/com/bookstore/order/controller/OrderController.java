package com.bookstore.order.controller;

import com.bookstore.order.entity.Order;
import com.bookstore.order.entity.OrderDTO;
import com.bookstore.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/get-all")
    public List<OrderDTO> getAllOrders(@RequestParam(required = false) String requestId,
                                       @RequestParam(required = false) Boolean isMultiple) {
        return isMultiple != null && isMultiple ? orderService.getAllOrdersMultiple(requestId)
                                                : orderService.getAllOrdersSingle(requestId);
    }
}
