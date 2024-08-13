package com.anyGroup.Music_Test.dto;

import com.anyGroup.Music_Test.entities.RoleEntity;

public class UserResponse1 {

    private String username;

    private String email;

    private String password;

    private RoleEntity role;

    //Getters & Setters
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    public RoleEntity getRole() { return this.role; }
    public void setRole(RoleEntity role) { this.role = role; }
    //Getters & Setters
}
