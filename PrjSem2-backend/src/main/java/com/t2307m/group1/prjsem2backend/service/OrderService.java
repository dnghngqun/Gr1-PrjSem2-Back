package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    // tạo mới đơn hàng
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    //

    @Transactional
    public Order updateOrder(int userId, Order orderUpdate) {
        Optional<Order> orderOpt = orderRepository.findByAccount_Id(userId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();

        if (orderUpdate.getTotalPrice() != null) {
            order.setTotalPrice(orderUpdate.getTotalPrice());
        }
        if (orderUpdate.getStatus() != null) {
            order.setStatus(orderUpdate.getStatus());
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(int userId) {
        if (!orderRepository.existsByAccountId(userId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteByAccount_Id(userId);
    }

    //lấy toàn bộ orderDetail
    public Optional<List<Order>> getAllOrders() {
        return Optional.of(orderRepository.findAll());
    }

    //lấy theo id
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    //lấy theo id khách hàng
    public Optional<Order> getOrderByUserId(int userId) {
        return orderRepository.findByAccount_Id(userId);
    }

    //lấy theo trạng thái

    public Optional<List<Order>> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
}
