package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.model.User;

import javax.persistence.Column;

public class CurrentUser {

    private Long id;
    private String role;
    private String name;
    private String surname;
    private String email;

    public CurrentUser(){
    }

    public CurrentUser(User user){
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
