package com.example.demo.serviceInterface;

import com.example.demo.entity.GroupsEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface CreateGroupService {
    public boolean created(HttpServletRequest request, GroupsEntity groupsEntity);
}
