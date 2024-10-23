package com.example.demo.service;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceInterface.CreateGroupService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CreateGroupServiceImpl implements CreateGroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepo memberRepo;
    @Override
    public boolean created(HttpServletRequest request, GroupsEntity groupsEntity){
        if(groupRepo.findByGroupname(groupsEntity.getGroupname())!=null){
            return  false;
        }
        UserEntity userEntity=userRepo.findByUid(jwtUtil.extractUserIdFromRequest(request));
        groupsEntity.setCreator(userEntity);
        groupRepo.save(groupsEntity);
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setMember(userEntity);
        memberEntity.setGroupsEntity(groupsEntity);
        memberRepo.save(memberEntity);
        return true;
    }

}
