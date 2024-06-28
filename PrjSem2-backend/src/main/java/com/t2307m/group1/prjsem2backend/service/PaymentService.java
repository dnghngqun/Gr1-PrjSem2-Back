package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Payment;
import com.t2307m.group1.prjsem2backend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(int id, Payment updatedPayment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(id);
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();
            existingPayment.setAccount(updatedPayment.getAccount());
            existingPayment.setOrderDetail(updatedPayment.getOrderDetail());
            existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
            existingPayment.setAmount(updatedPayment.getAmount());
            existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
            existingPayment.setStatus(updatedPayment.getStatus());
            return paymentRepository.save(existingPayment);
        } else {
            return null; // hoặc có thể ném một exception tùy theo logic ứng dụng
        }
    }

    public boolean deletePayment(int id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            paymentRepository.delete(paymentOptional.get());
            return true;
        } else {
            return false; // hoặc có thể ném một exception tùy theo logic ứng dụng
        }
    }
}

