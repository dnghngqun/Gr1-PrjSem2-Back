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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Date;
import java.util.*;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
@RestController//báo cho spring biết đây là controller
@RequestMapping("/api/v1/accounts")// connect to api = this link
public class AccountController {
    private final AccountService accountService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${imgur.client.id}")
    private String imgurClientId;

    @Autowired
    public AccountController(AccountService accountService,EmailService emailService, PasswordResetTokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
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




    public String uploadImageToImgur(MultipartFile image) throws Exception {
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
        Random random = new Random();
        int code = 100000 + random.nextInt(1000000);
        String token = String.valueOf(code);
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
        account.setPassword(passwordEncoder.encode(newPassword));
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

    @PostMapping("/instructor")
    public ResponseEntity<ResponseObject> createInstructor(@RequestPart("instructor") InstructorDTO instructorDTO,
                                                           @RequestPart("image") MultipartFile image){
        try {
            String imageUrl = uploadImageToImgur(image);
            instructorDTO.setImageLink(imageUrl);
            Account newStaff = accountService.createInstructor(instructorDTO);
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
    public ResponseEntity<ResponseObject> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccount();
        if (accounts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Get Account Err", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
          new ResponseObject("ok", "Get accounts successfully!", accounts)
        );
    }


    @PutMapping("/changePassword")
    public ResponseEntity<ResponseObject> changePassword(@RequestParam String identify,@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session){
        Optional<Account> changePassword = accountService.changePassword(identify,oldPassword, newPassword);
        if (changePassword.isPresent()){
            session.setAttribute("user", changePassword.get());
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAccount(@PathVariable int id){
        try {
            accountService.deleteAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete account successfully!", "")
            );
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Failed to delete account!", "")
            );
        }
    }

    @DeleteMapping("/instructor/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAccountInstructor(@PathVariable int id){
        try {
            accountService.deleteAccountInstructorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete account instructor successfully!", "")
            );
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Failed to delete account!", "")
            );
        }
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
        Account user = (Account) session.getAttribute("user");
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
    @PutMapping("/admin/updateInformation/{id}")
    public ResponseEntity<ResponseObject> updateInformationAndPw(@PathVariable int id, @RequestBody Account account, HttpSession session){
        Optional<Account> updateInformation = accountService.updateAccountWithPassword(id, account);
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

    @GetMapping("/students")
    public ResponseEntity<ResponseObject> getAllStudents() {
        List<Account> students = accountService.getAllCustomers();
        if (students.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Get students Err", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get all students successfully!", students)
        );
    }

    @GetMapping("/instructors")
    public ResponseEntity<ResponseObject> getAllInstructors() {
        List<Account> instructors = accountService.getAllInstructor();
        if (instructors.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Get Instructors Err", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get all instructor successfully!", instructors)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseObject> getAccountByEmail(@PathVariable String email){
        Optional<Account> account = accountService.findByEmail(email);
        return account.map(a -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "get account by email successfully!", a)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Account not found!", "")
        ));
    }

    @GetMapping("/count-customers")
    public long countCustomers() {
        return accountService.countCustomers();
    }

    @PostMapping("/createDefaultDBAccount")
    public String createDefaultAccounts() {
        //tạo 3 tk user mặc định hongquan, dothao, phamhoang
        Account hongquan = new Account();
        hongquan.setUserName("hongquan");
        hongquan.setEmail("quandhth2304004@fpt.edu.vn");
        hongquan.setPassword("123456"); // Mật khẩu sẽ được mã hóa trong AccountService
        hongquan.setPhoneNumber("0383240511");
        hongquan.setFullName("Dang Hong Quan");
        hongquan.setBirthday(Date.valueOf("2000-01-01"));
        hongquan.setRole("customer"); // hoặc "staff", "admin" tùy theo yêu cầu
        accountService.registerCustomer(hongquan);
        //dothao
        Account dothao = new Account();
        dothao.setUserName("dothao");
        dothao.setEmail("thaodtth2304010@fpt.edu.vn");
        dothao.setPassword("123456"); // Mật khẩu sẽ được mã hóa trong AccountService
        dothao.setPhoneNumber("0348279942");
        dothao.setFullName("Do Thi Thao");
        dothao.setBirthday(Date.valueOf("2000-01-01"));
        dothao.setRole("customer"); // hoặc "staff", "admin" tùy theo yêu cầu
        accountService.registerCustomer(dothao);
        //phamhoang
        Account phamhoang = new Account();
        phamhoang.setUserName("phamhoang");
        phamhoang.setEmail("hoangpnth2304021@fpt.edu.vn");
        phamhoang.setPassword("123456"); // Mật khẩu sẽ được mã hóa trong AccountService
        phamhoang.setPhoneNumber("0915298826");
        phamhoang.setFullName("Pham Nhat Hoang");
        phamhoang.setBirthday(Date.valueOf("2000-01-01"));
        phamhoang.setRole("customer"); // hoặc "staff", "admin" tùy theo yêu cầu
        accountService.registerCustomer(phamhoang);

        //tạo 10 tk user pass 123456
        for (int i = 1; i <= 10; i++) {
            Account account = new Account();
            account.setUserName("user" + i);
            account.setEmail("user" + i + "@example.com");
            account.setPassword("123456"); // Mật khẩu sẽ được mã hóa trong AccountService
            account.setPhoneNumber("123456789" + i);
            account.setFullName("User " + i);
            account.setBirthday(Date.valueOf("2000-01-01"));
            account.setRole("customer"); // hoặc "staff", "admin" tùy theo yêu cầu
            accountService.registerCustomer(account);
        }

        // Tạo tài khoản admin
        Account admin = new Account();
        admin.setUserName("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword("123456"); // Mật khẩu sẽ được mã hóa trong AccountService
        admin.setPhoneNumber("00000000000");
        admin.setFullName("Admin");
        admin.setBirthday(Date.valueOf("1999-01-01"));
        accountService.createAdmin(admin);

        // Tạo các tài khoản instructor
        // Tạo các tài khoản instructor
        String[][] instructors = {
                {"johnsmith", "123456", "John Smith", "johnsmith_ielts@example.com", "0123456789", "1990-01-01", "https://i.imgur.com/TIuVvui.png?1", "IELTS instructor with 10 years of teaching and exam preparation experience.", "Male", "IELTS"},
                {"sarahlee", "123456", "Sarah Lee", "sarahlee_toeic@example.com", "0911222333", "1990-01-01", "https://i.imgur.com/ADQUqCv.png?1", "TOEIC instructor with 8 years of experience and effective teaching methods.", "Female", "TOEIC"},
                {"davidtaylor", "123456", "David Taylor", "davidtaylor_toeic@example.com", "0933444555", "1990-01-01", "https://i.imgur.com/TiY9PuH.jpg", "TOEIC exam preparation specialist with many years of experience.", "Male", "TOEIC"},
                {"emilyjohnson", "123456", "Emily Johnson", "emilyjohnson_ielts@example.com", "0987654321", "1990-01-01", "https://i.imgur.com/tATWZoE.jpg", "IELTS teacher with international certification and many years of experience.", "Female", "IELTS"},
                {"lauramartinez", "123456", "Laura Martinez", "lauramartinez_toeic@example.com", "0944555666", "1990-01-01", "https://i.imgur.com/jQjsgtw.jpg", "TOEIC teacher with high-quality lessons and students achieving high scores.", "Female", "TOEIC"},
                {"michaelbrown", "123456", "Michael Brown", "michaelbrown_ielts@example.com", "0123987654", "1990-01-01", "https://i.imgur.com/yXleNYE.jpg", "IELTS specialist with modern teaching methods and extensive experience.", "Male", "IELTS"},
                {"sophiadavis", "123456", "Sophia Davis", "sophiadavis_ielts@example.com", "0987123456", "1990-01-01", "https://i.imgur.com/vwx4lTP.jpg", "Master of Linguistics, specializing in IELTS exam preparation.", "Female", "IELTS"},
                {"jameswilson", "123456", "James Wilson", "jameswilson_ielts@example.com", "0912345678", "1990-01-01", "https://i.imgur.com/us80Xxs.jpg", "IELTS instructor with many students achieving high scores.", "Male", "IELTS"},
                {"robertanderson", "123456", "Robert Anderson", "robertanderson_toeic@example.com", "0955666777", "1990-01-01", "https://i.imgur.com/qLfUpbC.jpg", "Master of Linguistics, specializing in TOEIC exam preparation.", "Male", "TOEIC"},
                {"emmathomas", "123456", "Emma Thomas", "emmathomas_toeic@example.com", "0966777888", "1990-01-01", "https://i.imgur.com/LuXZpZA.jpg", "TOEIC instructor with many years of experience and advanced teaching methods.", "Female", "TOEIC"}
        };


        for (String[] instructor : instructors) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setUsername(instructor[0]);
            instructorDTO.setPassword(instructor[1]);
            instructorDTO.setFullName(instructor[2]);
            instructorDTO.setEmail(instructor[3]);
            instructorDTO.setPhoneNumber(instructor[4]);
            instructorDTO.setBirthday(Date.valueOf(instructor[5]));
            instructorDTO.setImageLink(instructor[6]);
            instructorDTO.setBio(instructor[7]);
            instructorDTO.setGender(instructor[8]);
            instructorDTO.setClassify(instructor[9]);
            accountService.createInstructor(instructorDTO);
        }

        return "Default accounts created successfully!";
    }

}
