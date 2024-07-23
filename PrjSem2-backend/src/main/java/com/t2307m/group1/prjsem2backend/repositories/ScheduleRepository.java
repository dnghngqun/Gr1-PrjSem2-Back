package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findScheduleByAClassId(int aClassId);
}
