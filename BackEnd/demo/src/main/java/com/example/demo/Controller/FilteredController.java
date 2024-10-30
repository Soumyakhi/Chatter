package com.example.demo.Controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.serviceInterface.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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
        boolean created= createGroupService.created(request, groupsEntity);
        if(created){
            return ResponseEntity.status(HttpStatus.OK).body("Group Created");
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
    public ResponseEntity<Object> isCreator(@PathVariable long uid,@PathVariable long groupid){
        boolean isMember= checkMemberService.checkIfMember(uid, groupid);
        if(isMember){
            return ResponseEntity.status(HttpStatus.OK).body("true");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
    }
    @Autowired
    SearchAddMemberService searchAddMemberService;
    @GetMapping("/searchNonMembers/{query}/{groupid}")
    public Set<UserEntity> isCreator(@PathVariable String query, @PathVariable long groupid){
        return searchAddMemberService.search(query,groupid);
    }

}
