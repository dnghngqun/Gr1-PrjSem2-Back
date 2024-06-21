package com.t2307m.group1.prjsem2backend.repositories;


import com.t2307m.group1.prjsem2backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);
    Optional<List<Course>> findByPrice(double price);
    Optional<List<Course>> findByStatus(int status);
    Optional<List<Course>> findByNameAndStatus(String name, int status);
}
