package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//báo cho spring biết đây là controller
@RequestMapping("/api/v1/account")// connect to api = this link
public class AccountController {
    //DI = Dependency Injection
    @Autowired // đối tượng sẽ được tạo ra khi app được ta, tạo 1 lần
    private AccountRepository accountRepository;


}
