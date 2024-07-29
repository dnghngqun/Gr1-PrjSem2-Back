package com.t2307m.group1.prjsem2backend.controllers;

import com.paypal.api.payments.*;
import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.model.Payment;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.OrderDetailService;
import com.t2307m.group1.prjsem2backend.service.OrderService;
import com.t2307m.group1.prjsem2backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderDetailService orderDetailService;
    private final OrderService orderService;
    @Autowired
    public PaymentController(PaymentService paymentService, OrderDetailService orderDetailService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderDetailService = orderDetailService;
        this.orderService= orderService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/paymentId/{paymentId}")
    public ResponseEntity<Payment> getPaymentByPaymentId(@PathVariable("paymentId") String paymentId) {
        Optional<Payment> payment = paymentService.getPaymentByPaymentId(paymentId);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> executePayment(@RequestBody Payment payment) {
        try {
            // Thực hiện thanh toán và lưu thông tin thanh toán
            payment = paymentService.executePayment(payment);

            // Cập nhật trạng thái OrderDetail sau khi thanh toán thành công
            orderDetailService.updateStatus(payment.getOrderDetail().getId(), 1);

            //update total amount on order
            com.t2307m.group1.prjsem2backend.model.Order newOrder = new Order();
            newOrder.setTotalPrice(payment.getAmount());
            orderService.updateOrder(payment.getAccount().getId(), newOrder);

            // Trả về thông tin Payment đã thanh toán thành công
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Payment successful!", payment)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "Payment execution failed!", "")
            );
        }
    }

    @GetMapping("/paymentOrder")
    public ResponseEntity<ResponseObject> getAllPaymentByIdDesc (){
        List<Payment> payments = paymentService.getAllPaymentsByIdDesc();
        if (payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Payment not found!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", " get Payment successful!", payments)
        );
    }

    @GetMapping("/revenue/yearly")
    public Map<String, Object> getYearlyBreakup() {
        return paymentService.getYearlyBreakup();
    }

    @GetMapping("/revenue/monthly")
    public Map<String, Object> getMonthlyEarnings(@RequestParam int year) {
        return paymentService.getMonthlyEarnings(year);
    }
}
