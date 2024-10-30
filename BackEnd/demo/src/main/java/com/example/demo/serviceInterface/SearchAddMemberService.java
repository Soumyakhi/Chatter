package com.example.demo.serviceInterface;
import com.example.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface SearchAddMemberService {
    Set<UserEntity> search(String query,long groupId);
}
