package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Enrollment;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // API lấy danh sách tất cả đăng ký học tập
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get all enrollments successfully!", enrollments)
        );
    }

    // API lấy thông tin đăng ký học tập bởi ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEnrollmentById(@PathVariable int id) {
        Optional<Enrollment> enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get enrollment by ID successfully!", enrollment.get())
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Enrollment not found!", "")
            );
        }
    }

    // API lưu thông tin đăng ký học tập mới
    @PostMapping("")
    public ResponseEntity<ResponseObject> saveEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment newEnrollment = enrollmentService.saveEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Save enrollment successfully!", newEnrollment)
        );
    }

    // API xóa thông tin đăng ký học tập
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEnrollment(@PathVariable int id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete enrollment successfully!", "")
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseObject> getEnrollmentByUserId(@PathVariable int id) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentByAccountId(id);
        if (enrollments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Enrollment not found!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get enrollment by ID successfully!", enrollments)
        );
    }
}
