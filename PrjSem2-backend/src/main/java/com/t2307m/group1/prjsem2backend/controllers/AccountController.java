package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.LoginRequest;
import com.t2307m.group1.prjsem2backend.model.PasswordResetToken;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.repositories.PasswordResetTokenRepository;
import com.t2307m.group1.prjsem2backend.service.AccountService;
import com.t2307m.group1.prjsem2backend.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.UUID;

@RestController//báo cho spring biết đây là controller
@RequestMapping("/api/v1/accounts")// connect to api = this link
public class AccountController {
    private final AccountService accountService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;
    @Autowired
    public AccountController(AccountService accountService,EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        this.accountService = accountService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestParam String email){
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        if(optionalAccount.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              new ResponseObject("failed", "User not found!", "")
            );
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, optionalAccount.get());
        tokenRepository.save(resetToken);//post to db
        emailService.sendPasswordResetToken(email, token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Password reset token sent to your email!", "")
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject> resetPassword(@RequestParam String token, @RequestParam String newPassword){
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);

        if(optionalToken.isEmpty() || optionalToken.get().isExpired()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Invalid or exprired token","")
            );
        }

        Account account = optionalToken.get().getAccount();
        account.setPassword(newPassword);
        accountService.save(account);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Password has been reset!", "")
        );
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerCustomer(@RequestBody Account account){
        try {
            Account newAccount = accountService.registerCustomer(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newAccount)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }

    @PostMapping("/staff")
    public ResponseEntity<ResponseObject> createStaff(@RequestBody Account account){
        try {
            Account newStaff = accountService.createStaff(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newStaff)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<ResponseObject> createAdmin(@RequestBody Account account){
        try{
            Account newAdmin = accountService.createAdmin(account);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create account successfully!", newAdmin)
            );
        }catch (Exception e){
            System.err.println("Err: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "create account not success!", "")
            );
        }
    }


    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllAccounts(Pageable pageable){
        Page<Account> accounts = accountService.getAllAccount(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
          new ResponseObject("ok", "Get accounts successfully!", accounts)
        );
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseObject> changePassword(@RequestParam String identify,@RequestParam String oldPassword, @RequestParam String newPassword){
        Optional<Account> changePassword = accountService.changePassword(identify,oldPassword, newPassword);
        if (changePassword.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "change password successfully!", changePassword.get())
            );
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("failed", "username or password is incorrect, please check!", "")
        );
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<ResponseObject> deleteAccount(@RequestBody LoginRequest loginRequest){
        boolean status = accountService.deleteAccount(loginRequest.getIdentify().trim(), loginRequest.getPassword().trim());
        if (status){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete account succesfully!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("failed", "username or password is incorrect!", "")
        );

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<Account> account = accountService.login(loginRequest.getIdentify().trim(), loginRequest.getPassword().trim());
        if (account.isPresent()) {
            session.setAttribute("user", account.get()); //lưu thông tin người dùng vào session
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Login successfully!", account.get())
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseObject("failed", "Invalid username or password!", "")
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(HttpSession session){
        session.invalidate();//xoá session khi logout
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Logout successfully!","")
        );
    }

    @GetMapping("/currentUser")
    public ResponseEntity<ResponseObject> getCurrentUser(HttpSession session){
        Object user = session.getAttribute("user");
        if (user != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Current user retrieved successfully",user)
            );

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "No user logged in!", "")
        );
    }

    @PutMapping("/updateInformation/{id}")
    public ResponseEntity<ResponseObject> updateInformation(@PathVariable int id, @RequestBody Account account){
        Optional<Account> updateInformation = accountService.updateAccount(id, account);
        return updateInformation.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Information Success!", value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("failed", "Update Information Failed!","")
        ));
    }


}
