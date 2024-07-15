package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Payment;
import com.t2307m.group1.prjsem2backend.repositories.OrderDetailRepository;
import com.t2307m.group1.prjsem2backend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByPaymentId(String paymentId){
        return paymentRepository.findByPaymentId(paymentId);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(String paymentId, Payment updatedPayment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findByPaymentId(paymentId);
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();

            if (updatedPayment.getAccount() != null) {
                existingPayment.setAccount(updatedPayment.getAccount());
            }
            if (updatedPayment.getOrderDetail() != null) {
                existingPayment.setOrderDetail(updatedPayment.getOrderDetail());
            }
            if (updatedPayment.getPaymentMethod() != null) {
                existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
            }
            if (updatedPayment.getAmount() != null) {
                existingPayment.setAmount(updatedPayment.getAmount());
            }
            if (updatedPayment.getPaymentDate() != null) {
                existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
            }
            if (updatedPayment.getStatus() != null) {
                existingPayment.setStatus(updatedPayment.getStatus());
            }

            return paymentRepository.save(existingPayment);
        } else {
            throw new RuntimeException("Payment not found with paymentId: " + paymentId);
        }
    }


    public boolean deletePayment(String paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findByPaymentId(paymentId);
        if (paymentOptional.isPresent()) {
            paymentRepository.delete(paymentOptional.get());
            return true;
        } else {
            return false; // hoặc có thể ném một exception tùy theo logic ứng dụng
        }
    }
    @Transactional
    public Payment executePayment(Payment payment) {
        // Thực hiện lưu thông tin thanh toán vào cơ sở dữ liệu
        payment.setPaymentMethod(payment.getPaymentMethod());
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis())); // Thiết lập ngày thanh toán
        payment.setStatus(1); // Đánh dấu thanh toán thành công

        return paymentRepository.save(payment);
    }
}

