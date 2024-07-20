package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
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
    private final OrderRepository orderRepository;
    //DI = Dependency Injection
    @Autowired
    public AccountService( AccountRepository accountRepository, OrderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
    }

    public Account registerCustomer(Account account){
        account.setPassword(account.getPassword());
        Order order = new Order();
        order.setAccount(account);
        Account newAcc = accountRepository.save(account);
        orderRepository.save(order);
        return newAcc;
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


    public Optional<Account> changePassword(String identify, String oldPassword , String newPassword){
        Optional<Account> optionalAccount = accountRepository.findByUserName(identify);
        if (optionalAccount.isEmpty()){
            optionalAccount = accountRepository.findByEmail(identify);
        }
        if(optionalAccount.isEmpty()){
            optionalAccount = accountRepository.findByPhoneNumber(identify);
        }
        //ktra xem optional có chứa dữ liệu ko, return true or false
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();//get value of optional
            if (!account.getPassword().equals(oldPassword)) return Optional.empty();
            account.setPassword(newPassword);
            accountRepository.save(account);
            return Optional.of(account); //return value of account
        }
        return Optional.empty();//return empty optional
    }

    public Optional<Account> updateAccount(int id, Account account) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) throw new RuntimeException("Account not found");

        Account newAccount = accountOpt.get();

        if (account.getFullName() != null) {
            newAccount.setFullName(account.getFullName());
        }
        if (account.getBirthday() != null) {
            newAccount.setBirthday(account.getBirthday());
        }
        if (account.getEmail() != null) {
            newAccount.setEmail(account.getEmail());
        }
        if (account.getPhoneNumber() != null) {
            newAccount.setPhoneNumber(account.getPhoneNumber());
        }
        if (account.getImageAccount() != null) {
            newAccount.setImageAccount(account.getImageAccount());
        }

        accountRepository.save(newAccount);
        return Optional.of(newAccount);
    }

    public List<Account> getAllCustomers() {
        return accountRepository.findAllCustomers();
    }

    public Optional<Account> login(String identify, String password) {
        // Giả sử Account có các thuộc tính: username, email, phoneNumber, password
        Optional<Account> account = accountRepository.findByUserName(identify);
        if (account.isEmpty()) account = accountRepository.findByPhoneNumber(identify);
        if (account.isEmpty()) account = accountRepository.findByEmail(identify);

        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return Optional.of(account.get());
        }
        return Optional.empty();
    }

    public boolean deleteAccount(String identify, String password){
        Optional<Account> optionalAccount = accountRepository.findByUserName(identify);
        if (optionalAccount.isEmpty()){
            optionalAccount = accountRepository.findByEmail(identify);
        }
        if(optionalAccount.isEmpty()){
            optionalAccount = accountRepository.findByPhoneNumber(identify);
        }
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            if(!account.getPassword().equals(password)) return false;

            accountRepository.delete(account);
            return true;
        }
        return false;
    }

    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }

    public Optional<Account> findById(int id){
        return accountRepository.findById(id);
    }




}
