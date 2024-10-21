package com.example.demo.repo;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUid(Long id);
    public UserEntity findByEmailAndPassword(String email,String password);
    public UserEntity findByEmailOrUserName(String email,String userName);
}
