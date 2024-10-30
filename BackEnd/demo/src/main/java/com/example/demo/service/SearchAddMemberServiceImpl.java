package com.example.demo.service;

import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceInterface.SearchAddMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class SearchAddMemberServiceImpl implements SearchAddMemberService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    GroupRepo groupRepo;
    @Override
    public Set<UserEntity> search(String query,long groupId) {
        query=query.trim();
        Set<UserEntity> userSet;
        if(query.length()<3){
            userSet=userRepo.searchRight(query);
        }
        else {
            userSet=userRepo.searchLeftRight(query);
        }
        GroupsEntity groupsEntity=groupRepo.findByGroupid(groupId);
        Set<UserEntity> userSetFiltered=new HashSet<>();
        for(UserEntity userEntity:userSet){
            MemberEntity memberEntity=memberRepo.findByMemberAndGroupsEntity(userEntity,groupsEntity);
            if(memberEntity==null){
                userSetFiltered.add(userEntity);
            }
        }
        return userSetFiltered;
    }
}
