package com.example.demo.serviceInterface;

import com.example.demo.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface AddMemberService {
    public boolean addMember(HttpServletRequest request, @RequestBody MemberDTO memberDTO);
}
