package com.soniya.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soniya.book.entity.Book;
import com.soniya.book.entity.Customer;
import com.soniya.book.entity.Order;
import com.soniya.book.entity.OrderDTO;

@Service
public class OrderServiceImpl {

    List<Order> orderList = new ArrayList<>();
    int i = 1;

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CustomerServiceImpl customerService;

    public Order addOrder(Order order) {
        order.setId(i);
        i++;
        orderList.add(order);
        return order;
    }

    public Order getOrder(int id) {
        for (Order o : orderList) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            OrderDTO od = new OrderDTO();
            od.setQuantity(o.getQuantity());
            Book b = bookService.getBook(o.getBookId());
            od.setBookName(b.getName());
            Customer c = customerService.getCustomer(o.getCustomerId());
            od.setCustomerName(c.getName());
            orderDtoList.add(od);
        }
        return orderDtoList;
    }
}
