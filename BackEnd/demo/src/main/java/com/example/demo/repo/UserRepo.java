package com.example.demo.repo;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUid(Long id);
    public UserEntity findByEmailAndPassword(String email,String password);
    public UserEntity findByEmailOrUserName(String email,String userName);
    @Query(value = "SELECT * FROM user  WHERE user_name like %?1%", nativeQuery = true)
    public Set<UserEntity> searchLeftRight(String userName);
    @Query(value = "SELECT * FROM user  WHERE user_name like ?1%", nativeQuery = true)
    public Set<UserEntity> searchRight(String userName);
}
