package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Payment;
import com.t2307m.group1.prjsem2backend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RevenueService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Map<String, Double> getMonthlyRevenue(int year) {
        Map<String, Double> monthlyRevenue = new HashMap<>();

        for (int month = 1; month <= 12; month++) {
            List<Payment> payments = paymentRepository.findPaymentsByYearAndMonth(year, month);
            double totalRevenue = payments.stream()
                    .mapToDouble(Payment::getAmount)
                    .sum();
            monthlyRevenue.put(String.format("%02d", month), totalRevenue);
        }

        return monthlyRevenue;
    }
}
