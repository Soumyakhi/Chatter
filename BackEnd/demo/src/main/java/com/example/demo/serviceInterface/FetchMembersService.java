package com.example.demo.serviceInterface;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FetchMembersService {
    public List<MemberDTO> fetch(HttpServletRequest request, long groupName);
}
