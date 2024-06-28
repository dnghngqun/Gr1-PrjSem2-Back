package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.OrderDetail;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orderDetails")
public class OrderDetailsController {
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailsController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetail);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Create order detail successfully!", newOrderDetail)
            );
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Create Order Detail error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrderDetail(@PathVariable int id, @RequestBody OrderDetail orderDetailUpdate) {
        try {
            OrderDetail updateOrderDetail = orderDetailService.updateOrderDetail(id, orderDetailUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update order detail successfully!", updateOrderDetail)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Update Order Details Error!", "")
            );
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteOrderDetail(@PathVariable int id) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete order detail successfully!", "")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Delete order detail error!", "")
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        if (orderDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "No order details found", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query all order details successfully", orderDetails)
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderDetailById(@PathVariable int id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        return orderDetail.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query order detail by ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "Order detail not found", "")
                ));
    }
}
