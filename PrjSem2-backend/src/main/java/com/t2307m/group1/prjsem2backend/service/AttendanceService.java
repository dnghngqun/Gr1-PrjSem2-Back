package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Attendance;
import com.t2307m.group1.prjsem2backend.model.Schedule;
import com.t2307m.group1.prjsem2backend.repositories.AttendanceRepository;
import com.t2307m.group1.prjsem2backend.repositories.EnrollmentRepository;
import com.t2307m.group1.prjsem2backend.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, EnrollmentRepository enrollmentRepository, ScheduleRepository scheduleRepository) {
        this.attendanceRepository = attendanceRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.scheduleRepository = scheduleRepository;
    }


    // Lấy danh sách các buổi học đã điểm danh
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // Lưu thông tin điểm danh
    public Attendance saveAttendance(Integer enrollmentId, Integer scheduleId, String status) {
        if (enrollmentRepository.existsById(enrollmentId) && scheduleRepository.existsById(scheduleId)) {
            Attendance attendance = new Attendance();
            attendance.setEnrollment(enrollmentRepository.findById(enrollmentId).get());
            attendance.setSchedule(scheduleRepository.findById(scheduleId).get());
            attendance.setAttendanceStatus(status);
            return attendanceRepository.save(attendance);
        } else {
            throw new RuntimeException("Enrollment or Schedule not found");
        }
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

    public List<Schedule> findScheduleByClassId(int classId){
        return scheduleRepository.findScheduleByAClassId(classId);
    }
}
