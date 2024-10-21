package com.example.demo.Controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.*;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
        String token = loginService.doAuthenticate(userEntity.getEmail(), userEntity.getPassword());;
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserEntity userEntity) {
        int code=registerService.register(userEntity);
        if(code==200){
            return new ResponseEntity<>("Signup Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("email or username already present", HttpStatus.BAD_REQUEST);
    }

}