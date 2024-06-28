package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody Order order){
        try {
            Order newOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Create order successfully!", newOrder)
            );
        }catch (Exception e){
            System.err.println("Exception: "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Create Order error!", "")
            );
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseObject> updateOrder (@PathVariable int userId, @RequestBody Order orderUpdate){
        Order updateOrder = orderService.updateOrder(userId, orderUpdate);
        if (updateOrder != null){
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("ok", "update order successfully!",updateOrder)
            );
        }else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("Failed", "Update Order Error!", "")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@PathVariable int id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete order successfully!", "")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Delete Order Error!", "")
            );
        }
    }
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllOrders() {
        Optional<List<Order>> orders = orderService.getAllOrders();
        return orders.map(orderList -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query all orders successfully", orderList)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "No orders found", "")
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable int id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query order by ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "Order not found", "")
                ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getOrderByUserId(@PathVariable int userId) {
        Optional<Order> order = orderService.getOrderByUserId(userId);
        return order.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query order by user ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "Order not found", "")
                ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseObject> getOrdersByStatus(@PathVariable int status) {
        Optional<List<Order>> orders = orderService.getOrdersByStatus(status);
        return orders.map(orderList -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query orders by status successfully", orderList)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "No orders found with this status", "")
                ));
    }


}
