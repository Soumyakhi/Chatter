package com.example.demo.serviceInterface;


import com.example.demo.dto.LoginInfoDTO;

public interface LoginService {
    public LoginInfoDTO doAuthenticate(String email, String password);
}
