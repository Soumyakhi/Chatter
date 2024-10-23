package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceInterface.AddMemberService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AddMemberServiceImpl implements AddMemberService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepo memberRepo;
    @Override
    public boolean addMember(HttpServletRequest request, @RequestBody MemberDTO memberDTO){
        long creatorIdToken=jwtUtil.extractUserIdFromRequest(request);
        GroupsEntity group=groupRepo.findByGroupid(memberDTO.getGroupid());
        UserEntity member=userRepo.findByUid(memberDTO.getUid());
        if(group==null || creatorIdToken!=group.getCreator().getUid() || member==null || memberRepo.findByMemberAndGroupsEntity(member,group)!=null){
            return false;
        }
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setGroupsEntity(group);
        memberEntity.setMember(member);
        memberRepo.save(memberEntity);
        return  true;
    }
}
