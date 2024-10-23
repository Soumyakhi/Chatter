package com.example.demo.serviceInterface;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface LogoutService {
    public void logoutService(HttpServletRequest request);
}
