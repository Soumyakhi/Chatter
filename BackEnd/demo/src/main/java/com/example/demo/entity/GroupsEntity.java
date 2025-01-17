package com.example.demo.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "Groupsentity")
public class GroupsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupid;
    public Long getGroupid() {
        return groupid;
    }
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    @Column(nullable = false,unique = true)
    private String groupname;
    public UserEntity getCreator() {
        return creator;
    }
    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }
    @ManyToOne
    @JoinColumn(name = "creatorid", referencedColumnName = "uid", nullable = false)
    private UserEntity creator;
}
