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
import com.example.demo.utils.MyWebSocketHandler;
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
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;
    @Override
    public boolean addText(HttpServletRequest request, TextDTO textDTO) {
        long uid=jwtUtil.extractUserIdFromRequest(request);
        boolean isMember=checkMemberService.checkIfMember(uid,textDTO.getGroupid());
        if (isMember) {
            UserEntity userEntity = userRepo.findByUid(uid);
            GroupsEntity groupsEntity = groupRepo.findByGroupid(textDTO.getGroupid());
            TextEntity textEntity = new TextEntity();
            textEntity.setText(textDTO.getText());
            textEntity.setGroupsEntity(groupsEntity);
            textEntity.setUserEntity(userEntity);
            try {
                textRepo.save(textEntity);  // Save text to the database

                // Broadcast the text to all WebSocket sessions of the group
                myWebSocketHandler.sendTextToGroup(textDTO.getGroupid(), textDTO.getText(),textEntity.getUserEntity().getUid());
            } catch (Exception e) {
                e.printStackTrace();  // Log error if saving or broadcasting fails
                return false;
            }
            return true;  // Return true if text was added successfully
        }
        return false;
    }
}
