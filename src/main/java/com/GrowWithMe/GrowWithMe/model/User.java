package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "mydb")

public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId", nullable = false)
    private Integer userId;

    @JsonIgnore
    @Column(name = "userLogin", nullable = false, unique = true, updatable = false)
    private String userLogin;

    @Basic
    @Column(name = "userName", nullable = false)
    private String userName;
    @Basic
    @Column(name = "userSurname", nullable = false)
    private String userSurname;
    @Basic
    @JsonIgnore
    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="rolesToUsers",
            joinColumns=@JoinColumn(name="userId"),
            inverseJoinColumns = @JoinColumn(name="roleId"))
    private Set<Role> authorities;


    public User(){
        super();
        this.authorities = new HashSet<Role>();
    }

    public User(Integer userId, String userLogin, String userName, String userSurname, String userPassword, Set<Role> authorities) {
        super();
        this.userId = userId;
        this.userLogin = userLogin;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String getUsername() {
        return this.userLogin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
