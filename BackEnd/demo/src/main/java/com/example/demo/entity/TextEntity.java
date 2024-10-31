package com.example.demo.entity;
import jakarta.persistence.*;
@Entity
@Table(name="Texts")
public class TextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid;
    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid", nullable = false)
    private GroupsEntity groupsEntity;
    @Column(columnDefinition = "TEXT")
    private String text;

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public GroupsEntity getGroupsEntity() {
        return groupsEntity;
    }

    public void setGroupsEntity(GroupsEntity groupsEntity) {
        this.groupsEntity = groupsEntity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
