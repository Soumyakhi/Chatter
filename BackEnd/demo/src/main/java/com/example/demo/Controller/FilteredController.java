package com.example.demo.Controller;

import com.example.demo.dto.FetchGroupsDTO;
import com.example.demo.dto.FetchedTextDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.TextDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.TextEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.serviceInterface.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RequestMapping("/index")
@RestController
public class FilteredController {
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    @GetMapping("/home")
    public String hello(){
        return "hello";
    }
    @Autowired
    private LogoutService logout;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        logout.logoutService(request);
        return "logged out";
    }
    @Autowired
    private CreateGroupService createGroupService;
    @PostMapping("/createGroup")
    public ResponseEntity<Object> createGroup(HttpServletRequest request,@RequestBody GroupsEntity groupsEntity){
        GroupsEntity group= createGroupService.created(request, groupsEntity);
        if(group!=null){
            return ResponseEntity.status(HttpStatus.OK).body(group);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group already exists");
    }
    @Autowired
    private AddMemberService addMemberService;
    @PostMapping("/addMember")
    public ResponseEntity<Object> addMember(HttpServletRequest request,@RequestBody MemberDTO memberDTO){
        boolean added= addMemberService.addMember(request, memberDTO);
        if(added){
            return ResponseEntity.status(HttpStatus.OK).body("Member Added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can not add member");
    }
    @Autowired
    private CheckCreatorService checkCreatorService;
    @GetMapping("/isCreator")
    public ResponseEntity<Object> isCreator(HttpServletRequest request,@RequestParam long groupId){
        boolean isCreator= checkCreatorService.isCreator(request, groupId);
        if(isCreator){
            return ResponseEntity.status(HttpStatus.OK).body("true");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
    }
    @Autowired
    CheckMemberService checkMemberService;
    @GetMapping("/isMember/{uid}/{groupid}")
    public ResponseEntity<Object> isMember(@PathVariable long uid,@PathVariable long groupid){
        boolean isMember= checkMemberService.checkIfMember(uid, groupid);
        if(isMember){
            return ResponseEntity.status(HttpStatus.OK).body("true");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
    }
    @Autowired
    SearchAddMemberService searchAddMemberService;
    @GetMapping("/searchNonMembers/{query}/{groupid}")
    public Set<UserEntity> searchNonMembers(@PathVariable String query, @PathVariable long groupid){
        return searchAddMemberService.search(query,groupid);
    }
    @Autowired
    AddTextService addTextService;
    @PostMapping("/addText")
    public ResponseEntity<Object> addText(HttpServletRequest request, @RequestBody TextDTO textDTO){
        boolean added=addTextService.addText(request,textDTO);
        if(added){
            return ResponseEntity.status(HttpStatus.OK).body("text added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("could not add");
    }
    @Autowired
    FetchAllTextService fetchAllTextService;
    @GetMapping("/fetchAllTexts/{groupid}")
    public List<FetchedTextDTO> fetchAllTexts(HttpServletRequest request, @PathVariable long groupid){
        return fetchAllTextService.fetchTexts(request,groupid);
    }
    @Autowired
    FetchGroupsService fetchGroupsService;
    @GetMapping("/fetchGroups/{query}")
    public List<FetchGroupsDTO> fetchGroups(HttpServletRequest request,@PathVariable String query){
        return fetchGroupsService.fetchGroups(request,query);
    }
    @Autowired
    FetchMembersService fetchMembersService;
    @GetMapping("/fetchMembers/{groupid}")
    public List<MemberDTO> fetchMembers(HttpServletRequest request,@PathVariable long groupid){
        return fetchMembersService.fetch(request,groupid);
    }
}
