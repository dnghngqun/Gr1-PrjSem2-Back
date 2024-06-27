package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.lang.System.currentTimeMillis;
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private AccountRepository accountRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Order createOrder(int userId, double totalPrice, int status) {
        Optional<Account> account = accountRepository.findById(userId);
        Order order = new Order(account.get(),totalPrice,status);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(int userId, double totalPrice, int status) {
        Optional<Order> orderOpt = orderRepository.findByAccount_Id(userId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Order not found");
        }
        Order order = orderOpt.get();
        order.setTotalPrice(totalPrice);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(int userId) {
        if (!orderRepository.existsByAccountId(userId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteByAccount_Id(userId);
    }

    public Optional<List<Order>> getAllOrders() {
        return Optional.of(orderRepository.findAll());
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByUserId(int userId) {
        return orderRepository.findByAccount_Id(userId);
    }

    public Optional<List<Order>> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
}
