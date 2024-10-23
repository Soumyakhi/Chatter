package com.example.demo.serviceInterface;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface AddMemberService {
    public boolean addMember(HttpServletRequest request, @RequestBody MemberDTO memberDTO);
}
