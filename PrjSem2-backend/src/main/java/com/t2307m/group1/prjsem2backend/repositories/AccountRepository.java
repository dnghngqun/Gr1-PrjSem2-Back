package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUserName(String userName);
    Account findByEmail(String email);
    Account findByPhoneNumber(String phoneNumber);
}
