package com.ngon.backend.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String firstName;
    private String lastName;

    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(Role role) {this.role = role;}

    public String getUsername() {return this.username;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
    public Role getRole() {return this.role;}
}
