package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers", schema = "defaultdb")
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
    @JsonManagedReference
    private List<Client> clientList = new ArrayList<>();
}
