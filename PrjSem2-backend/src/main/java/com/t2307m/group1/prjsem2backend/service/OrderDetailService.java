package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Course;
import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.model.OrderDetail;
import com.t2307m.group1.prjsem2backend.repositories.CourseRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderDetailRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderDetailService {
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }


    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public Optional<OrderDetail> getOrderDetailById(int id) {
        return orderDetailRepository.findById(id);
    }


    @Transactional
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public OrderDetail updateOrderDetail(int id, OrderDetail orderDetailUpdate) {
        Optional<OrderDetail> orderDetailOpt = orderDetailRepository.findById(id);
        if (orderDetailOpt.isEmpty()) throw new RuntimeException("Order detail not found");


        OrderDetail orderDetail = orderDetailOpt.get();
        orderDetail.setDiscount(orderDetailUpdate.getDiscount());
        orderDetail.setQuantity(orderDetailUpdate.getQuantity());
        orderDetail.setTotalAmount(orderDetailUpdate.getTotalAmount());
        orderDetail.setStatus(orderDetailUpdate.getStatus());
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public void deleteOrderDetail(int id) {
        if (!orderDetailRepository.existsById(id)) throw new RuntimeException("Order detail not found");

        orderDetailRepository.deleteById(id);
    }

}
