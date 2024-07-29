
package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    // tạo 1 instructorService

    private final InstructorService instructorService;
    private final AccountController accountController;
    @Autowired
    public InstructorController(InstructorService instructorService, AccountController accountController) {
        this.instructorService = instructorService;
        this.accountController = accountController;
    }

    // Add new instructor
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestPart("instructor") Instructor instructor, @RequestPart("image")MultipartFile image) throws Exception {
        try {
            String imageLink = accountController.uploadImageToImgur(image);
            instructor.setImageLink(imageLink);
            Instructor newInstructor = instructorService.addInstructor(instructor);
            return ResponseEntity.ok(newInstructor);
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    // Find instructor by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Instructor> findInstructorByEmail(@PathVariable String email) {
        Optional<Instructor> instructorOptional = instructorService.findIntructorByEmail(email);
        if (instructorOptional.isPresent()) {
            return ResponseEntity.ok(instructorOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update instructor by email
    @PutMapping("/email/{email}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable String email, @RequestBody Instructor instructor) {
        boolean updated = instructorService.updateInstructor(email, instructor);
        if (updated) {
            return ResponseEntity.ok(instructor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructor();
        return ResponseEntity.ok(instructors);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseObject>  deleleByEmail(@PathVariable String email){
        try {
            instructorService.deleteInstructor(email);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
