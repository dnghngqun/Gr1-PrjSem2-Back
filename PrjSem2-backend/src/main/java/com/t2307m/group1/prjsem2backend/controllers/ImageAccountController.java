package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.ImageAccount;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.ImageAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/imageAccount")
public class ImageAccountController {
    private final ImageAccountService imageAccountService;

    @Autowired
    public ImageAccountController(ImageAccountService imageAccountService) {
        this.imageAccountService = imageAccountService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createImageAccount(@RequestBody ImageAccount imageAccount){
        try {
            ImageAccount newImageAccount = imageAccountService.saveImageAccount(imageAccount);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Create ImageAccount successfully!", newImageAccount)
            );
        }catch (Exception e){
            System.err.println("Exception: "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Create ImageAccount error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateImageAccount(@PathVariable int id, @RequestBody ImageAccount imageAccountUpdate){
        Optional<ImageAccount> updatedImageAccount = imageAccountService.getImageAccountById(id)
                .map(existingImageAccount -> {
                    existingImageAccount.setImageLink(imageAccountUpdate.getImageLink());
                    existingImageAccount.setAccount(imageAccountUpdate.getAccount());
                    return imageAccountService.updateImageAccount(existingImageAccount);
                });

        return updatedImageAccount.map(imageAccount -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update ImageAccount successfully!", imageAccount)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "ImageAccount not found!", "")
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteImageAccount(@PathVariable int id) {
        try {
            imageAccountService.deleteImageAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete ImageAccount successfully!", "")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Delete ImageAccount error!", "")
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllImageAccounts() {
        List<ImageAccount> imageAccounts = imageAccountService.getAllImageAccounts();
        return !imageAccounts.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query all ImageAccounts successfully", imageAccounts))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "No ImageAccounts found", "")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getImageAccountById(@PathVariable int id) {
        Optional<ImageAccount> imageAccount = imageAccountService.getImageAccountById(id);
        return imageAccount.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query ImageAccount by ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "ImageAccount not found", "")
                ));
    }
}
