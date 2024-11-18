package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.serviceInterface.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repo.UserRepo;
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserRepo userRepo;
    @Override
    public int register(UserEntity userEntity){
        UserEntity existingUser1=userRepo.findByEmail(userEntity.getEmail());
        UserEntity existingUser2=userRepo.findByUserName(userEntity.getUserName());
        if(existingUser1!=null || existingUser2!=null){
            return 400;
        }
        userRepo.save(userEntity);
        return  200;
    }

}
