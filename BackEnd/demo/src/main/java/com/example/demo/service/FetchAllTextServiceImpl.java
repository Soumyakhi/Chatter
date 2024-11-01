package com.example.demo.service;

import com.example.demo.dto.FetchedTextDTO;
import com.example.demo.entity.TextEntity;
import com.example.demo.repo.TextRepo;
import com.example.demo.serviceInterface.CheckMemberService;
import com.example.demo.serviceInterface.FetchAllTextService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchAllTextServiceImpl implements FetchAllTextService {
    @Autowired
    TextRepo textRepo;
    @Autowired
    CheckMemberService checkMemberService;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public List<FetchedTextDTO> fetchTexts(HttpServletRequest request, long groupId) {
        long uid=jwtUtil.extractUserIdFromRequest(request);
        if(checkMemberService.checkIfMember(uid,groupId)){
            List<FetchedTextDTO> fetchedTextDTOS=new ArrayList<>();
            for(TextEntity textEntity : textRepo.findAllByGroupsEntity_GroupidOrderByTid(groupId)){
                FetchedTextDTO fetchedTextDTO=new FetchedTextDTO();
                fetchedTextDTO.setTextId(textEntity.getTid());
                fetchedTextDTO.setText(textEntity.getText());
                fetchedTextDTO.setuId(textEntity.getUserEntity().getUid());
                fetchedTextDTO.setuName(textEntity.getUserEntity().getUserName());
                fetchedTextDTOS.add(fetchedTextDTO);
            }
            return fetchedTextDTOS;
        }
        return new ArrayList<>();
    }
}
