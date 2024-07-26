package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.AClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassRepository extends JpaRepository<AClass, Integer> {
    @Query("SELECT c FROM AClass c WHERE c.status = 1")
    List<AClass> getAClassByStatusIsStarted();

    List<AClass> getAClassByInstructorId(int instructorId);
}