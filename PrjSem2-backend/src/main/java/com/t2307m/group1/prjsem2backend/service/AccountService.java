package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    //DI = Dependency Injection
    @Autowired
    public AccountService( AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerCustomer(Account account){
        account.setPassword(account.getPassword());
        return accountRepository.save(account);
    }
    //role can use this function: admin
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

    public Page<Account> getAllAccount(Pageable pageable){
        return accountRepository.findAll(pageable);
    }


    public Optional<Account> findById(@PathVariable Integer id){
        return accountRepository.findById(id);
    }




}
