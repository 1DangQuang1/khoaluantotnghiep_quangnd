package com.example.restapi.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

@Entity
@javax.persistence.Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GeneratedValue.IDENTITY)
    private Long id;
    private String name;
    private String role;

    private Long id;
    private String name;
    private String role;
    private String username;
    private String password;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; } 
    
}   
