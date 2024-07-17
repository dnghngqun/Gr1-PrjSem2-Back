package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Attendance;
import com.t2307m.group1.prjsem2backend.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // Lấy danh sách các buổi học đã điểm danh
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // Lưu thông tin điểm danh
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // Tìm kiếm điểm danh theo ID
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    // Xóa thông tin điểm danh
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
    public List<Attendance> getAttendanceByEnrollmentId(int enrollmentId) {
        return attendanceRepository.getAttendanceByEnrollmentId(enrollmentId);
    }

}
