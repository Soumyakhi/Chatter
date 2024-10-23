package com.example.demo.service;

import com.example.demo.serviceInterface.LogoutService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private JwtUtil jwtUtil;
    public void logoutService(HttpServletRequest request){
        String uid=String.valueOf(jwtUtil.extractUserIdFromRequest(request));
        jwtUtil.removeToken(uid);
    }
}
