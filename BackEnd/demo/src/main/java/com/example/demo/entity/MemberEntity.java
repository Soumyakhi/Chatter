package com.example.demo.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    @ManyToOne
    @JoinColumn(name = "memberid", referencedColumnName = "uid", nullable = false)
    private UserEntity member;
    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false)
    private GroupsEntity groupsEntity;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public UserEntity getMember() {
        return member;
    }

    public void setMember(UserEntity member) {
        this.member = member;
    }

    public GroupsEntity getGroupsEntity() {
        return groupsEntity;
    }

    public void setGroupsEntity(GroupsEntity groupsEntity) {
        this.groupsEntity = groupsEntity;
    }
}
