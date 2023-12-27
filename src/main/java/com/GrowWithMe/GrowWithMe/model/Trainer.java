package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private int trainerId;
    @Basic
    @Column(name = "trainerUserId", nullable = false)
    private int trainerUserId;
    @Basic
    @Column(name = "trainerPhoneNumber", nullable = false)
    private int trainerPhoneNumber;
}
