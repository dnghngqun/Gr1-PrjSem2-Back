package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> getAttendanceByEnrollmentId(int enrollmentId);

    @Query("SELECT a FROM Attendance a " +
            "INNER JOIN Enrollment e ON a.enrollment.id = e.id " +
            "INNER JOIN Schedule s ON a.schedule.id = s.id " +
            "INNER JOIN AClass ac ON e.aClass.id = ac.id " +
            "WHERE ac.id = :classId " +
            "AND s.classDate = :classDate")
    List<Attendance> findAttendanceByClassIdAndDate(@Param("classId") int classId, @Param("classDate") Date classDate);

    Optional<Attendance> findByEnrollmentIdAndScheduleId(int enrollmentId, int scheduleId);
    @Query("SELECT a FROM Attendance a " +
            "INNER JOIN Enrollment e ON a.enrollment.id = e.id " +
            "INNER JOIN Schedule s ON a.schedule.id = s.id " +
            "INNER JOIN AClass ac ON e.aClass.id = ac.id " +
            "WHERE ac.id = :classId ")
    List<Attendance> findAllByClassId(int classId);

    @Query("SELECT a FROM Attendance a " +
            "INNER JOIN Enrollment e ON a.enrollment.id = e.id " +
            "INNER JOIN Account acc ON e.account.id = acc.id " +
            "WHERE acc.id = :studentId")
    List<Attendance> findByStudentId(@Param("studentId") int studentId);
}

