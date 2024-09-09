package com.bookstore.order.service;

import com.bookstore.order.entity.Order;
import com.bookstore.order.entity.OrderDTO;
import com.bookstore.order.entity.external.Book;
import com.bookstore.order.entity.external.Customer;
import com.bookstore.order.repo.OrderRepo;
import com.bookstore.order.repo.external.BookRepo;
import com.bookstore.order.repo.external.CustomerRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Order addOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        return orderRepo.save(order);
    }

    public Order getOrder(String id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }

    public List<OrderDTO> getAllOrdersMultiple(String requestId) {
        requestId = StringUtils.hasText(requestId) ? requestId : UUID.randomUUID().toString();
        logger.info("{} : getAllOrdersMultiple: Start get all orders", requestId);
        List<Order> orderList = orderRepo.findAll();
        logger.info("{} : Found all orders [{}]", requestId, orderList.size());

        String bookUri = "http://BOOK-SERVICE/book/get/";
        String customerUri = "http://CUSTOMER-SERVICE/customer/get/";

        List<OrderDTO> orderDtoList = new ArrayList<>();
        for (Order o : orderList) {
            try {
                OrderDTO od = new OrderDTO();
                od.setQuantity(o.getQuantity());
                od.setTotalPrice(o.getTotalPrice());

                // Get book details
                logger.info("{} : get book for bookId [{}]", requestId, o.getBookId());
                Book b = restTemplate.getForObject(bookUri + o.getBookId(), Book.class);
                if (b != null) {
                    od.setBookTitle(b.getTitle());
                } else {
                    logger.warn("{} : Book not found for bookId [{}]", requestId, o.getBookId());
                    od.setBookTitle("Unknown");
                }

                // Get customer details
                logger.info("{} : get customer for customerId [{}]", requestId, o.getCustomerId());
                Customer c = restTemplate.getForObject(customerUri + o.getCustomerId(), Customer.class);
                if (c != null) {
                    od.setCustomerName(c.getName());
                } else {
                    logger.warn("{} : Customer not found for customerId [{}]", requestId, o.getCustomerId());
                    od.setCustomerName("Unknown");
                }

                orderDtoList.add(od);
            } catch (RestClientException e) {
                logger.error("{} : Exception occurred while processing order [{}]: ", requestId, o, e);
            }
        }
        logger.info("{} : Completed processing orders", requestId);
        return orderDtoList;
    }

    public List<OrderDTO> getAllOrdersSingle(String requestId) {
        requestId = StringUtils.hasText(requestId) ? requestId : UUID.randomUUID().toString();
        logger.info("{} : getAllOrdersSingle: Start get all orders", requestId);
        List<Order> orderList = orderRepo.findAll();
        logger.info("{} : Found all orders [{}]", requestId, orderList.size());

        String bookUri = "http://BOOK-SERVICE/book/get-multiple/";
        String customerUri = "http://CUSTOMER-SERVICE/customer/get-multiple/";

        try {
            String bookIdList = orderList.stream().map(Order::getBookId).distinct().collect(Collectors.joining(","));
            HttpEntity<String> bookRequest = new HttpEntity<>(bookIdList);
            ResponseEntity<List<Book>> bookResponse = restTemplate.exchange(bookUri, HttpMethod.POST, bookRequest, new ParameterizedTypeReference<List<Book>>() {});
            List<Book> bookList = bookResponse.getBody();
            logger.info("{} : got book list for bookIds [{}]", requestId, bookList != null ? bookList.size() : 0);

            String customerIdList = orderList.stream().map(Order::getCustomerId).distinct().collect(Collectors.joining(","));
            HttpEntity<String> customerRequest = new HttpEntity<>(customerIdList);
           ResponseEntity<List<Customer>> customerResponse = restTemplate.exchange(customerUri, HttpMethod.POST, customerRequest, new ParameterizedTypeReference<List<Customer>>() {});
            List<Customer> customerList = customerResponse.getBody();
           logger.info("{} : got customer list for customerIds [{}]", requestId, customerList != null ? customerList.size() : 0);

            List<OrderDTO> orderDtoList = new ArrayList<>();
            for (Order o : orderList) {
                OrderDTO od = new OrderDTO();
                od.setQuantity(o.getQuantity());
                od.setTotalPrice(o.getTotalPrice());

                String bookTitle = bookList.stream().filter(b -> b.getId().equals(o.getBookId())).findFirst().map(Book::getTitle).orElse("Unknown");
                od.setBookTitle(bookTitle);

                String customerName = customerList.stream().filter(c -> c.getId().equals(o.getCustomerId())).findFirst().map(Customer::getName).orElse("Unknown");
                od.setCustomerName(customerName);

                orderDtoList.add(od);
            }
            logger.info("{} : Completed processing orders", requestId);
            return orderDtoList;
        } catch (RestClientException e) {
            logger.error("{} : Exception occurred while getting book/customer lists: ", requestId, e);
            return Collections.emptyList();
        }
    }

    public void addCustomerId(String customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customerRepo.save(customer);
    }

    public void addBookId(String bookId) {
        Book book = new Book();
        book.setId(bookId);
        bookRepo.save(book);
    }
}
