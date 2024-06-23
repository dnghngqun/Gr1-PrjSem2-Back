
package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    // tạo 1 instructorService

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // Add new instructor
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor) {
        Instructor newInstructor = instructorService.addInstructor(instructor);
        return ResponseEntity.ok(newInstructor);
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
}
