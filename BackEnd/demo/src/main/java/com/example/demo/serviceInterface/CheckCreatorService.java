package com.example.demo.serviceInterface;

import jakarta.servlet.http.HttpServletRequest;

public interface CheckCreatorService {
    public boolean isCreator(HttpServletRequest request, long groupId);
}
