package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.ImageAccount;
import com.t2307m.group1.prjsem2backend.repositories.ImageAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageAccountService {
    private final ImageAccountRepository imageAccountRepository;

    @Autowired
    public ImageAccountService(ImageAccountRepository imageAccountRepository) {
        this.imageAccountRepository = imageAccountRepository;
    }

    public ImageAccount saveImageAccount(ImageAccount imageAccount) {
        return imageAccountRepository.save(imageAccount);
    }

    public Optional<ImageAccount> getImageAccountById(int id) {
        return imageAccountRepository.findById(id);
    }

    public List<ImageAccount> getAllImageAccounts() {
        return imageAccountRepository.findAll();
    }

    public ImageAccount updateImageAccount(ImageAccount imageAccount) {
        return imageAccountRepository.save(imageAccount);
    }

    public void deleteImageAccount(int id) {
        imageAccountRepository.deleteById(id);
    }
}
