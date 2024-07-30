package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Enrollment;
import com.t2307m.group1.prjsem2backend.model.AClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    // Tìm kiếm các đăng ký học tập của một tài khoản (người dùng)
    List<Enrollment> findByAccount(Account account);

    // Tìm kiếm các đăng ký học tập của một lớp học
    @Query("SELECT e FROM Enrollment e WHERE e.aClass.id = :classId")
    List<Enrollment> findByAClassId(@Param("classId") int id);

    List<Enrollment> findByAccountId(int accountId);

//    DISTINCT e.account.id: lấy giá trị bản ghi và bỏ qua giá tri trùng lặp, count: đếm bản ghi
    @Query("SELECT COUNT(DISTINCT e.account.id) FROM Enrollment e WHERE e.aClass.instructor.id = :instructorId")
    long countDistinctStudentsByInstructorId(@Param("instructorId") int instructorId);
}
