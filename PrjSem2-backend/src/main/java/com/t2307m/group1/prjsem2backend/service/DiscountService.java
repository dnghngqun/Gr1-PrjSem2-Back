package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Discount;
import com.t2307m.group1.prjsem2backend.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    public Optional<Discount> findById(Integer id) {
        return discountRepository.findById(id);
    }

    public Discount create(Discount discount) {
        return discountRepository.save(discount);
    }

    public void deleteById(Integer id) {
        discountRepository.deleteById(id);
    }

    public Discount updateDiscount(Integer discountId, Discount updatedDiscount) {
        Optional<Discount> existingDiscountOptional = discountRepository.findById(discountId);
        if (existingDiscountOptional.isPresent()) {
            Discount existingDiscount = existingDiscountOptional.get();

            // Cập nhật thông tin chiết khấu
            if (updatedDiscount.getCode() != null) {
                existingDiscount.setCode(updatedDiscount.getCode());
            }
            if (updatedDiscount.getValue() != null) {
                existingDiscount.setValue(updatedDiscount.getValue());
            }
            if(updatedDiscount.getRemaining() != null){
                existingDiscount.setRemaining(updatedDiscount.getRemaining());
            }

            // Lưu lại thông tin đã cập nhật
            return discountRepository.save(existingDiscount);
        } else {
            throw new RuntimeException("Discount not found with id: " + discountId);
        }
    }

    public Optional<Discount> findByCode(String code) {
        return discountRepository.findByCode(code);
    }

}
