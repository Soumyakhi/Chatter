package com.example.demo.service;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
@Service
public class CreateGroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepo memberRepo;
    public boolean created(HttpServletRequest request, GroupsEntity groupsEntity){
        if(groupRepo.findByGroupname(groupsEntity.getGroupname())!=null){
            return  false;
        }
        String requestHeader = request.getHeader("Authorization");
        String token=requestHeader.substring(7);
        UserEntity userEntity=userRepo.findByUid(Long.parseLong(jwtUtil.extractUserId(token)));
        groupsEntity.setCreator(userEntity);
        groupRepo.save(groupsEntity);
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setMember(userEntity);
        memberEntity.setGroupsEntity(groupsEntity);
        memberRepo.save(memberEntity);
        return true;
    }

}
