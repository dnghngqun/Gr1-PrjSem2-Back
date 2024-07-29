package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.*;
import com.t2307m.group1.prjsem2backend.repositories.AttendanceRepository;
import com.t2307m.group1.prjsem2backend.repositories.EnrollmentRepository;
import com.t2307m.group1.prjsem2backend.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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

    public List<Schedule> findScheduleByClassDateAndInstructorId(Date classDate, int instructorId){

        return scheduleRepository.findScheduleByClassDateAndInstructorId(classDate,instructorId);
    }

    public List<Attendance> getAttendanceByClassId(int classId) {
        return attendanceRepository.findAllByClassId(classId);
    }

    public List<Schedule> findByClassDate (Date classDate){
        return scheduleRepository.findByClassDate(classDate);
    }

    public List<Attendance> saveBulkAttendance(List<AttendanceDTO> attendances) {
        List<Attendance> savedAttendances = new ArrayList<>();

        for (AttendanceDTO attendanceDTO : attendances) {
            Integer enrollmentId = attendanceDTO.getEnrollmentId();
            Integer scheduleId = attendanceDTO.getScheduleId();
            String status = attendanceDTO.getStatus();

            if (enrollmentRepository.existsById(enrollmentId) && scheduleRepository.existsById(scheduleId)) {
                Attendance attendance = new Attendance();
                attendance.setEnrollment(enrollmentRepository.findById(enrollmentId).get());
                attendance.setSchedule(scheduleRepository.findById(scheduleId).get());
                attendance.setAttendanceStatus(status);
                savedAttendances.add(attendanceRepository.save(attendance));
            } else {
                throw new RuntimeException("Enrollment or Schedule not found for ID: " + enrollmentId + ", " + scheduleId);
            }
        }
        return savedAttendances;
    }

    public List<Attendance> updateBulkAttendance(List<AttendanceDTO> attendances) {
        List<Attendance> savedAttendances = new ArrayList<>();
        for (AttendanceDTO attendanceDTO : attendances) {
            Integer enrollmentId = attendanceDTO.getEnrollmentId();
            Integer scheduleId = attendanceDTO.getScheduleId();
            String status = attendanceDTO.getStatus();

            Optional<Attendance> attendanceOpt = attendanceRepository.findByEnrollmentIdAndScheduleId(enrollmentId,scheduleId);

            if(attendanceOpt.isPresent()){
                Attendance attendance = attendanceOpt.get();
                attendance.setAttendanceStatus(status);
                savedAttendances.add(attendanceRepository.save(attendance));
            }
             else {
                throw new RuntimeException("Attendance not found for Enrollment ID: " + enrollmentId + " and Schedule ID: " + scheduleId);
            }
        }
        return savedAttendances;
    }

    public List<Attendance> getAttendanceByClassIdAndDate(int classId, Date classDate) {
        return attendanceRepository.findAttendanceByClassIdAndDate(classId, classDate);
    }

    public List<Attendance> getAttendanceByStudentId(int studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
}
