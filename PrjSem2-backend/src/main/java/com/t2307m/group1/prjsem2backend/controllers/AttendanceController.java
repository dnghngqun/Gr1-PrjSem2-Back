package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.*;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
import com.t2307m.group1.prjsem2backend.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final InstructorRepository instructorRepository;
    @Autowired
    public AttendanceController(AttendanceService attendanceService, InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok().body(attendances);
    }

    // Lưu thông tin điểm danh
    @PostMapping("/add")
    public ResponseEntity<Attendance> addAttendance(@RequestParam Integer enrollmentId,
                                                    @RequestParam Integer scheduleId,
                                                    @RequestParam String status) {
        Attendance addedAttendance = attendanceService.saveAttendance(enrollmentId,scheduleId,status);
        return ResponseEntity.status(HttpStatus.OK).body(addedAttendance);
    }
    @PostMapping("/add/bulk")
    public ResponseEntity<ResponseObject> addBulkAttendance(@RequestBody List<AttendanceDTO> attendances){
        List<Attendance> attendanceList =  attendanceService.saveBulkAttendance(attendances);
        if(attendanceList.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Error to save attendance", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "save attendance success!", attendanceList)
        );
    }

    @PutMapping("/update/bulk")
    public ResponseEntity<ResponseObject> updateBulkAttendance(@RequestBody List<AttendanceDTO> attendances){
        List<Attendance> attendanceList = attendanceService.updateBulkAttendance(attendances);
        if(attendanceList.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Error to update attendance", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update attendance success!", attendanceList)
        );
    }

    // Lấy thông tin điểm danh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        return attendance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Xóa thông tin điểm danh
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok().body("Deleted attendance with id: " + id);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<ResponseObject> getAttendanceByEnrollmentId(@PathVariable int enrollmentId) {
        List<Attendance> attendances = attendanceService.getAttendanceByEnrollmentId(enrollmentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "find attendance by enrollment id",attendances)
        );
    }

    @GetMapping("/schedule/class/{classId}")
    public ResponseEntity<ResponseObject> getScheduleByClassId(@PathVariable int classId){
        List<Schedule> schedules = attendanceService.findScheduleByClassId(classId);
        if (schedules.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          new ResponseObject("failed", "Schedule not found!", "")
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Find schedule by class id",schedules)
        );
    }


    @GetMapping("/schedule")
    public ResponseEntity<ResponseObject> getScheduleByClassDate
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate classDate,
             @RequestParam String email){
        Optional<Instructor> instructorOpt = instructorRepository.findByEmail(email);
        if(instructorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Instructor not found!", "")
            );
        }
        List<Schedule> schedule = attendanceService.findScheduleByClassDateAndInstructorId(Date.valueOf(classDate), instructorOpt.get().getId());
        if(schedule.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found schedule", "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get schedule succesfully!", schedule)
        );

    }

    @GetMapping("/class/{classId}/date/{classDate}")
    public ResponseEntity<List<Attendance>> getAttendanceByClassIdAndDate(
            @PathVariable int classId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate classDate) {

        List<Attendance> attendances = attendanceService.getAttendanceByClassIdAndDate(classId, Date.valueOf(classDate));

        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(attendances);
        }

        return ResponseEntity.ok(attendances);
    }


}

