package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping("/monthly")
    public Map<String, Double> getMonthlyRevenue(@RequestParam("year") int year) {
        return revenueService.getMonthlyRevenue(year);
    }
}
