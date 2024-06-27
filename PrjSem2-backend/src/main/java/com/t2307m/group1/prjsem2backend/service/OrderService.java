//package com.t2307m.group1.prjsem2backend.service;
//
//import com.t2307m.group1.prjsem2backend.model.Order;
//import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//import static java.lang.System.currentTimeMillis;
//@Service
//public class OrderService {
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Transactional
//    public Order createOrder(int userId, double totalPrice, int status) {
//        Timestamp currentTimestamp = new Timestamp(currentTimeMillis());
//        Order order = new Order();
//        order.setUserId(userId);
//        order.setTotalPrice(totalPrice);
//        order.setCreatedAt(currentTimestamp);
//        order.setUpdateAt(currentTimestamp);
//        order.setStatus(status);
//        return orderRepository.save(order);
//    }
//
//    @Transactional
//    public Order updateOrder(int id, double totalPrice, int status) {
//        Optional<Order> orderOpt = orderRepository.findById(id);
//        if (!orderOpt.isPresent()) {
//            throw new RuntimeException("Order not found");
//        }
//
//        Order order = orderOpt.get();
//        order.setTotalPrice(totalPrice);
//        order.setStatus(status);
//        order.setUpdateAt(new Timestamp(System.currentTimeMillis()));
//        return orderRepository.save(order);
//    }
//
//    @Transactional
//    public void deleteOrder(int id) {
//        if (!orderRepository.existsById(id)) {
//            throw new RuntimeException("Order not found");
//        }
//        orderRepository.deleteById(id);
//    }
//
//    public Optional<List<Order>> getAllOrders() {
//        return Optional.of(orderRepository.findAll());
//    }
//
//    public Optional<Order> getOrderById(int id) {
//        return orderRepository.findById(id);
//    }
//
//    public Optional<List<Order>> getOrdersByUserId(int userId) {
//        return orderRepository.findByUserId(userId);
//    }
//
//    public Optional<List<Order>> getOrdersByStatus(int status) {
//        return orderRepository.findByStatus(status);
//    }
//}
