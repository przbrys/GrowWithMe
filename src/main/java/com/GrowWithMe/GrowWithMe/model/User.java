package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "users", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId", nullable = false)
    private int userId;
    @Basic
    @Column(name = "userName", nullable = false, length = 45)
    private String userName;
    @Basic
    @Column(name = "userSurname", nullable = false, length = 45)
    private String userSurname;
    @Basic
    @Column(name = "userPassword", nullable = false, length = 260)
    private String userPassword;

}
