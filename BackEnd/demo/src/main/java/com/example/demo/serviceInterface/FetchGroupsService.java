package com.example.demo.serviceInterface;

import com.example.demo.dto.FetchGroupsDTO;
import com.example.demo.entity.GroupsEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FetchGroupsService {
    List<FetchGroupsDTO> fetchGroups(HttpServletRequest request);
}
