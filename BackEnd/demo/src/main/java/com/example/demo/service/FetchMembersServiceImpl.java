package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceInterface.FetchMembersService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FetchMembersServiceImpl implements FetchMembersService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MemberRepo memberRepo;
    @Override
    public List<MemberDTO> fetch(HttpServletRequest request, long groupid) {
        long reqUid= jwtUtil.extractUserIdFromRequest(request);
        UserEntity reqUser=userRepo.findByUid(reqUid);
        if(memberRepo.findByMemberAndGroupsEntity(reqUser,groupRepo.findByGroupid(groupid))==null){
            return new ArrayList<>();
        }
        List<MemberEntity> members=memberRepo.findByGroupsEntity_Groupid(groupid);
        List<MemberDTO> memberDTOS=new ArrayList<>();
        for(MemberEntity member:members){
            MemberDTO mem=new MemberDTO();
            mem.setUid(member.getMember().getUid());
            mem.setUname(member.getMember().getUserName());
            mem.setGroupid(groupid);
            memberDTOS.add(mem);
        }
        return memberDTOS;
    }
}
