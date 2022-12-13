package com.csci201.backend.controler;

import com.csci201.backend.model.LoginRequestModel;
import com.csci201.backend.model.RegisterRequestModel;
import com.csci201.backend.service.LoginRegisterServices;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class LoginRegisterController {
    @Autowired
    LoginRegisterServices service = new LoginRegisterServices();
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login/{username}/{password}")
    int Login(@PathVariable String username, @PathVariable String password){
        if (username == null || password == null){
            return -1;
        }
        return service.LoginService(username, password);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/register/{username}/{password}/{email}")
    int Register(@PathVariable String username, @PathVariable String password, @PathVariable String email){
        if (username == null || password == null || email == null){
            return -1;
        }
        return service.RegisterService(username, password, email);
    }

}
