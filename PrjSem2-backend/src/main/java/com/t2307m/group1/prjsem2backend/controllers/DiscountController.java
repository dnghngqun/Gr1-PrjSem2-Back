package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Discount;
import com.t2307m.group1.prjsem2backend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Integer id) {
        Optional<Discount> discount = discountService.findById(id);
        return discount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountService.create(discount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Integer id, @RequestBody Discount discountDetails) {
        try {
            Discount updatedDiscount = discountService.updateDiscount(id, discountDetails);
            return ResponseEntity.ok(updatedDiscount);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        Optional<Discount> discount = discountService.findById(id);
        if (discount.isPresent()) {
            discountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/code/{code}")
    public ResponseEntity<Discount> getDiscountByCode(@PathVariable String code) {
        Optional<Discount> discount = discountService.findByCode(code.toUpperCase());
        return discount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
