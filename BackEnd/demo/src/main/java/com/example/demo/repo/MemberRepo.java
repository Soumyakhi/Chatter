package com.example.demo.repo;

import com.example.demo.entity.GroupsEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByGroupsEntity_Groupid(Long groupid);
    MemberEntity findByMemberAndGroupsEntity(UserEntity member, GroupsEntity groupsEntity);
}
