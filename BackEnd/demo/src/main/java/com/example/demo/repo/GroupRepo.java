package com.example.demo.repo;

import com.example.demo.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<GroupsEntity, Long> {
    public GroupsEntity findByGroupname(String groupName);
    public GroupsEntity findByGroupid(long groupid);
    @Query(value = "SELECT Groupsentity.* FROM Groupsentity,members WHERE Groupsentity.groupid = members.groupid and members.memberid=?1 and Groupsentity.groupname like ?2", nativeQuery = true)
    List<GroupsEntity> fetchGroups(long uid,String groupname);
}
