package com.example.demo.serviceInterface;

import com.example.demo.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
    public int register(UserEntity userEntity);
}
