package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.model.InstructorDTO;
import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
import com.t2307m.group1.prjsem2backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final InstructorRepository instructorRepository;
    //DI = Dependency Injection
    @Autowired
    public AccountService( AccountRepository accountRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, InstructorRepository instructorRepository) {
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.instructorRepository = instructorRepository;
    }

    public Account registerCustomer(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Order order = new Order();
        order.setAccount(account);
        Account newAcc = accountRepository.save(account);
        orderRepository.save(order);
        return newAcc;
    }
    //role can use this function: admin
    @Transactional
    public Account createInstructor(InstructorDTO instructorDTO){
        Account account = new Account();
        account.setEmail(instructorDTO.getEmail());
        account.setPhoneNumber(instructorDTO.getPhoneNumber());
        account.setBirthday(instructorDTO.getBirthday());
        account.setFullName(instructorDTO.getFullName());
        account.setUserName(instructorDTO.getUsername());
        account.setImageAccount(instructorDTO.getImageLink());
        account.setRole("instructor");
        account.setPassword(passwordEncoder.encode(instructorDTO.getPassword()));

        Optional<Instructor> instructorOpt = instructorRepository.findByEmail(instructorDTO.getEmail());
        if (instructorOpt.isEmpty()) {
            Instructor instructor = new Instructor();
            instructor.setName(instructorDTO.getFullName());
            instructor.setBio(instructorDTO.getBio());
            instructor.setEmail(instructorDTO.getEmail());
            instructor.setGender(instructorDTO.getGender());
            instructor.setPhoneNumber(instructorDTO.getPhoneNumber());
            instructor.setClassify(instructorDTO.getClassify());
            instructor.setImageLink(instructorDTO.getImageLink());
            instructorRepository.save(instructor);
        }
        return accountRepository.save(account);
    }

    public Account createAdmin(Account account){
        account.setRole("admin");
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
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
            if (!passwordEncoder.matches(oldPassword, account.getPassword())) return Optional.empty();
            account.setPassword(passwordEncoder.encode(newPassword));
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
    public List<Account> getAllInstructor() {
        return accountRepository.findALlInstructor();
    }

    public Optional<Account> login(String identify, String password) {
        // Giả sử Account có các thuộc tính: username, email, phoneNumber, password
        Optional<Account> account = accountRepository.findByUserName(identify);
        if (account.isEmpty()) account = accountRepository.findByPhoneNumber(identify);
        if (account.isEmpty()) account = accountRepository.findByEmail(identify);

        if (account.isPresent() && passwordEncoder.matches(password, account.get().getPassword())) {
            return Optional.of(account.get());
        }
        return Optional.empty();
    }
    public Optional<Account> updateAccountWithPassword(int id, Account updatedAccount) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) throw new RuntimeException("Account not found");

        Account existingAccount = accountOpt.get();

        if (updatedAccount.getFullName() != null) {
            existingAccount.setFullName(updatedAccount.getFullName());
        }
        if (updatedAccount.getBirthday() != null) {
            existingAccount.setBirthday(updatedAccount.getBirthday());
        }
        if (updatedAccount.getEmail() != null) {
            existingAccount.setEmail(updatedAccount.getEmail());
        }
        if (updatedAccount.getPhoneNumber() != null) {
            existingAccount.setPhoneNumber(updatedAccount.getPhoneNumber());
        }
        if (updatedAccount.getImageAccount() != null) {
            existingAccount.setImageAccount(updatedAccount.getImageAccount());
        }
        if (updatedAccount.getPassword() != null) {
            // Mã hóa mật khẩu mới trước khi lưu
            existingAccount.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
        }

        accountRepository.save(existingAccount);
        return Optional.of(existingAccount);
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
    @Transactional
    public void deleteAccountById(int id){
        orderRepository.deleteByAccount_Id(id);
        accountRepository.deleteAccountById(id);
    }

    @Transactional
    public void deleteAccountInstructorById(int id){
        accountRepository.deleteAccountById(id);
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
