package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.serviceInterface.LoginService;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.example.demo.repo.UserRepo;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private UserRepo userRepo;
    @Override
    public String doAuthenticate(String email, String password) {
        UserEntity userEntity=userRepo.findByEmailAndPassword(email,password);
        if(userEntity==null || jwtUtil.isLoggedin(userEntity.getUid().toString())){
            throw new BadCredentialsException("bad credentials");
        }
        Long uid=userEntity.getUid();
        return this.jwtUtil.generateToken(uid.toString());
    }

}
