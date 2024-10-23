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
        if(userRepo.findByEmailOrUserName(userEntity.getEmail(),userEntity.getUserName())!=null){
            return 400;
        }
        userRepo.save(userEntity);

        return  200;
    }

}
