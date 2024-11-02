package com.example.demo.service;

import com.example.demo.dto.FetchGroupsDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.TextEntity;
import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.TextRepo;
import com.example.demo.serviceInterface.FetchGroupsService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchGroupsServiceImpl implements FetchGroupsService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    TextRepo textRepo;
    @Override
    public List<FetchGroupsDTO> fetchGroups(HttpServletRequest request) {
        long uid=jwtUtil.extractUserIdFromRequest(request);
        List<GroupsEntity> groupsEntities=groupRepo.fetchGroups(uid);
        List<FetchGroupsDTO> groups=new ArrayList<>();
        for(GroupsEntity groupsEntity:groupsEntities){
            FetchGroupsDTO fetchGroupsDTO=new FetchGroupsDTO();
            fetchGroupsDTO.setGroupName(groupsEntity.getGroupname());
            TextEntity textEntity=textRepo.findTopByGroupsEntity_GroupidOrderByTidDesc(groupsEntity.getGroupid());
            if(textEntity==null){
                fetchGroupsDTO.setText("");
                fetchGroupsDTO.setTextId(Integer.MIN_VALUE);
            }
            else{
                fetchGroupsDTO.setText(textEntity.getText());
                fetchGroupsDTO.setTextId(textEntity.getTid());
            }
            groups.add(fetchGroupsDTO);
        }
        groups.sort((a, b) -> Long.compare(b.getTextId(), a.getTextId()));
        return groups;
    }
}
