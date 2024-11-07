package com.example.demo.Controller;

import com.example.demo.repo.GroupRepo;
import com.example.demo.repo.MemberRepo;
import com.example.demo.serviceInterface.CheckMemberService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebSocketConnectionController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CheckMemberService checkMemberService;

    // No need for SimpMessagingTemplate in simple WebSocket handling
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @MessageMapping("/index/sendMessage/{groupId}")
    public void sendMessage(@Payload String message, @RequestHeader("Authorization") String authHeader,
                            @DestinationVariable Long groupId) {
        // Extract user info from JWT token
        String token = authHeader.substring(7); // Remove "Bearer " from the token
        String userId = jwtUtil.extractUserId(token);

        // Check if the user is a member of the group
        boolean isMember = checkMemberService.checkIfMember(Long.parseLong(userId), groupId);

        if (isMember) {
            // Use the custom WebSocketHandler to broadcast the message to the group
            myWebSocketHandler.sendTextToGroup(groupId, message);
        } else {
            System.out.println("User is not a member of the group.");
        }
    }

}