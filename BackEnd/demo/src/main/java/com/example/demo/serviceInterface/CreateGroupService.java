package com.example.demo.serviceInterface;

import com.example.demo.entity.GroupsEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface CreateGroupService {
    public GroupsEntity created(HttpServletRequest request, GroupsEntity groupsEntity);
}
