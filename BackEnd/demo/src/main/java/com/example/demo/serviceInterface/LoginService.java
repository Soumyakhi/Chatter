package com.example.demo.serviceInterface;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    public String doAuthenticate(String email, String password);
}
