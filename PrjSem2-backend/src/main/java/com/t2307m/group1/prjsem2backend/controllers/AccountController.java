package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.*;
import com.t2307m.group1.prjsem2backend.repositories.PasswordResetTokenRepository;
import com.t2307m.group1.prjsem2backend.service.AccountService;
import com.t2307m.group1.prjsem2backend.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
@RestController//báo cho spring biết đây là controller
@RequestMapping("/api/v1/accounts")// connect to api = this link
public class AccountController {
    private final AccountService accountService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    @Value("${imgur.client.id}")
    private String imgurClientId;

    @Autowired
    public AccountController(AccountService accountService,EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        this.accountService = accountService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @PutMapping("/{userId}/uploadAvatar")
    public ResponseEntity<ResponseObject> uploadAvatar(@PathVariable int userId, @RequestParam("image") MultipartFile image,  HttpSession session) {
        Optional<Account> accountOptional = accountService.findById(userId);
        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "User not found!", "")
            );
        }

        Account account = accountOptional.get();
        try {
            String imageUrl = uploadImageToImgur(image);
            account.setImageAccount(imageUrl);
            accountService.save(account);

            // Cập nhật session với thông tin tài khoản mới
            session.setAttribute("user", account);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Upload avatar successfully!", account)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Upload avatar failed!", e.getMessage())
            );
        }
    }

    private String uploadImageToImgur(MultipartFile image) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + imgurClientId);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new MultipartInputStreamFileResource(image.getInputStream(), image.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity("https://api.imgur.com/3/upload", requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
            return (String) data.get("link");
        } else {
            throw new Exception("Failed to upload image to Imgur");
        }
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
    public ResponseEntity<ResponseObject> updateInformation(@PathVariable int id, @RequestBody Account account, HttpSession session){
        Optional<Account> updateInformation = accountService.updateAccount(id, account);
        if (updateInformation.isPresent()) {
            // Lấy thông tin người dùng đã được cập nhật
            Account updatedAccount = updateInformation.get();

            // Cập nhật lại session với thông tin người dùng mới
            session.setAttribute("user", updatedAccount);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update Information Success!", updatedAccount)
            );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Update Information Failed!","")
            );
        }
    }


}
