package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    //DI = Dependency Injection
    @Autowired
    public AccountService( AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerCustomer(Account account){
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }
    //role can using this function: admin
    public Account createStaff(Account account){
        account.setRole("staff");
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }

    public Account createAdmin(Account account){
        account.setRole("admin");
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }


}
