package com.example.demo.serviceInterface;

import com.example.demo.dto.TextDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

public interface AddTextService {
    public boolean addText(HttpServletRequest request, TextDTO textDTO);
}
