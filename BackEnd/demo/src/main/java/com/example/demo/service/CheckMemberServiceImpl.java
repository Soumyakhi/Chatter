package com.example.demo.service;

import com.example.demo.entity.MemberEntity;
import com.example.demo.repo.MemberRepo;
import com.example.demo.serviceInterface.CheckMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckMemberServiceImpl implements CheckMemberService {
    @Autowired
    MemberRepo memberRepo;
    @Override
    public boolean checkIfMember(long uid, long groupId) {
        List<MemberEntity> members=memberRepo.findByGroupsEntity_Groupid(groupId);
        for(MemberEntity memberEntity:members){
            if(memberEntity.getMember().getUid()==uid){
                return  true;
            }
        }
        return false;
    }
}
