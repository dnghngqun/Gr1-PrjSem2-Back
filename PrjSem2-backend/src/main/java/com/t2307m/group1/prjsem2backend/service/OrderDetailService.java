package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.model.OrderDetail;
import com.t2307m.group1.prjsem2backend.repositories.OrderDetailRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public Optional<OrderDetail> getOrderDetailById(int id) {
        return orderDetailRepository.findById(id);
    }

    @Transactional
    public OrderDetail createOrderDetail(int orderId, int courseId, double discount, int quantity, double totalAmount, int status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setCourseId(courseId);
        orderDetail.setDiscount(discount);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalAmount(totalAmount);
        orderDetail.setStatus(status);
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public OrderDetail updateOrderDetail(int id, double discount, int quantity, double totalAmount, int status) {
        Optional<OrderDetail> orderDetailOpt = orderDetailRepository.findById(id);
        if (!orderDetailOpt.isPresent()) {
            throw new RuntimeException("Order detail not found");
        }

        OrderDetail orderDetail = orderDetailOpt.get();
        orderDetail.setDiscount(discount);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalAmount(totalAmount);
        orderDetail.setStatus(status);
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public void deleteOrderDetail(int id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new RuntimeException("Order detail not found");
        }
        orderDetailRepository.deleteById(id);
    }

}
