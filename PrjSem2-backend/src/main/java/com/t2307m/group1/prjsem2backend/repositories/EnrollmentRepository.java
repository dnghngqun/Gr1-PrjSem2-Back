package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Enrollment;
import com.t2307m.group1.prjsem2backend.model.AClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    // Tìm kiếm các đăng ký học tập của một tài khoản (người dùng)
    List<Enrollment> findByAccount(Account account);

    // Tìm kiếm các đăng ký học tập của một lớp học
    List<Enrollment> findByAClass(AClass aClass);
}
