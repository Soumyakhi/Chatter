package com.example.demo.service;

import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    @Autowired
    private JwtUtil jwtUtil;
    public void logoutService(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        String token=requestHeader.substring(7);
        String uid=jwtUtil.extractUserId(token);
        jwtUtil.removeToken(uid);
    }
}
