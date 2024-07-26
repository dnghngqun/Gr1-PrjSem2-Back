package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.AClass;
import com.t2307m.group1.prjsem2backend.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findScheduleByAClassId(int aClassId);
    @Query("SELECT s FROM AClass c JOIN Schedule s ON c.id = s.aClass.id WHERE s.classDate = :classDate AND c.status = 1 AND c.instructor.id = :instructorId")
    List<Schedule> findScheduleByClassDateAndInstructorId(@Param("classDate") Date date ,@Param("instructorId") int instructorId);
    List<Schedule> findByClassDate(Date date);

}
