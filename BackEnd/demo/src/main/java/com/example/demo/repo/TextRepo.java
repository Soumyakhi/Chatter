package com.example.demo.repo;
import com.example.demo.entity.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextRepo extends JpaRepository<TextEntity, Long> {
    List<TextEntity> findAllByGroupsEntity_GroupidOrderByTid(long groupId);
    TextEntity findTopByGroupsEntity_GroupidOrderByTidDesc(long groupId);

}
