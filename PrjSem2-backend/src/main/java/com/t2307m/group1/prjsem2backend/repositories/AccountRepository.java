package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserName(String userName);
    //Optional để đại diện cho một giá trị có thể có hoặc không có, thay vì sử dụng null trực tiếp.
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhoneNumber(String phoneNumber);


    @Query("SELECT a FROM Account a WHERE a.role = 'customer'")
    List<Account> findAllCustomers();
}
