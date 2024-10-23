package com.example.demo.service;

import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.serviceInterface.CheckCreatorService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckCreatorServiceImpl implements CheckCreatorService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public boolean isCreator(HttpServletRequest request, long groupId){
        String requestHeader = request.getHeader("Authorization");
        String token=requestHeader.substring(7);
        long uid=Long.parseLong(jwtUtil.extractUserId(token));
        GroupsEntity groupsEntity=groupRepo.findByGroupid(groupId);
        if(groupsEntity==null){
            return false;
        }
        return uid==groupsEntity.getCreator().getUid();
    }
}
