package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients", schema = "defaultdb")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clientId", nullable = false)
    private Integer clientId;
    @Basic
    @Column(name = "clientHeight", nullable = false)
    private Integer clientHeight;
    @Basic
    @Column(name = "clientPhoneNumber", nullable = true)
    private Integer clientPhoneNumber;

    @OneToOne
    @JoinColumn(name = "clientUserId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "clientTrainerId", referencedColumnName = "trainerId")
    private Trainer trainer;

    @OneToMany(mappedBy = "client")
    private List<BodyInformation> bodyInformationList = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Report> reportsList = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Survey> surveyList = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<DietPlan> dietPlanList = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<TrainingPlan> trainingPlanList = new ArrayList<>();
}
