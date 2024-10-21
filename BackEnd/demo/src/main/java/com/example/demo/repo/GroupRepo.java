package com.example.demo.repo;

import com.example.demo.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<GroupsEntity, Long> {
    public GroupsEntity findByGroupname(String groupName);
    public GroupsEntity findByGroupid(long groupid);
}
