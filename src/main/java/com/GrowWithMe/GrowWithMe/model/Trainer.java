package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trainers", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Trainer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trainerId", nullable = false)
    private Integer trainerId;
    @Basic
    @Column(name = "trainerPhoneNumber", nullable = false)
    private Integer trainerPhoneNumber;

    @OneToOne
    @JoinColumn(name = "trainerUserId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "trainer")
    private List<Client> clientList = new ArrayList<>();
}
