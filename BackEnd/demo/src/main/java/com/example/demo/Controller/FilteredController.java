package com.example.demo.Controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.GroupsEntity;
import com.example.demo.serviceInterface.AddMemberService;
import com.example.demo.serviceInterface.CreateGroupService;
import com.example.demo.serviceInterface.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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
}
