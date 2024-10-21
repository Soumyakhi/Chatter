package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repo.UserRepo;
@Service
public class RegisterService {
    @Autowired
    UserRepo userRepo;
    public int register(UserEntity userEntity){
        if(userRepo.findByEmailOrUserName(userEntity.getEmail(),userEntity.getUserName())!=null){
            return 400;
        }
        userRepo.save(userEntity);

        return  200;
    }

}
