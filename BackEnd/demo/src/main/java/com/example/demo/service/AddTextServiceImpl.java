package com.example.demo.service;
import com.example.demo.dto.TextDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.TextEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.repo.TextRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.serviceInterface.AddTextService;
import com.example.demo.serviceInterface.CheckMemberService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.http.HttpRequest;
@Service
public class AddTextServiceImpl implements AddTextService {
    @Autowired
    TextRepo textRepo;
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CheckMemberService checkMemberService;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public boolean addText(HttpServletRequest request, TextDTO textDTO) {
        long uid=jwtUtil.extractUserIdFromRequest(request);
        boolean isMember=checkMemberService.checkIfMember(uid,textDTO.getGroupid());
        if(isMember){
            UserEntity userEntity=userRepo.findByUid(uid);
            GroupsEntity groupsEntity=groupRepo.findByGroupid(textDTO.getGroupid());
            TextEntity textEntity=new TextEntity();
            textEntity.setText(textDTO.getText());
            textEntity.setGroupsEntity(groupsEntity);
            textEntity.setUserEntity(userEntity);
            try {
                textRepo.save(textEntity);
            }
            catch (Exception e){
                return false;
            }
            return true;
        }
        return false;
    }
}
