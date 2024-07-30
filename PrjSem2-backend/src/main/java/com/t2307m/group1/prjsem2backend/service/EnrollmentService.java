package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.AClass;
import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Enrollment;
import com.t2307m.group1.prjsem2backend.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // Lấy danh sách tất cả các đăng ký học tập
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Lấy thông tin đăng ký học tập bởi ID
    public Optional<Enrollment> getEnrollmentById(int id) {
        return enrollmentRepository.findById(id);
    }

    // Lấy danh sách đăng ký học tập của một tài khoản (người dùng)
    public List<Enrollment> getEnrollmentsByAccount(Account account) {
        return enrollmentRepository.findByAccount(account);
    }

    // Lấy danh sách đăng ký học tập của một lớp học
    public List<Enrollment> getEnrollmentsByClassId(int id) {
        return enrollmentRepository.findByAClassId(id);
    }

    // Lưu thông tin đăng ký học tập mới
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    // Xóa thông tin đăng ký học tập
    public void deleteEnrollment(int id) {
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> getEnrollmentByAccountId(int id) {
        List<Enrollment> enrollments = enrollmentRepository.findByAccountId(id);
        return  enrollments;
    }

    public long getUniqueStudentCountByInstructorId(int instructorId) {
        return enrollmentRepository.countDistinctStudentsByInstructorId(instructorId);
    }
}
