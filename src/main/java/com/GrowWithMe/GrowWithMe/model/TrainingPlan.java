package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainingplans", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrainingPlan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trainingPlanId", nullable = false)
    private Integer trainingPlanId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainingPlanName", nullable = false)
    private String trainingPlanName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "trainingClientId", referencedColumnName = "clientId")
    private Client client;

    @ManyToMany()
    @JoinTable(name = "trainingPlansToExercises",
            joinColumns = @JoinColumn(name = "trainingPlansToExercisesTrainingPlanId"),
            inverseJoinColumns = @JoinColumn(name = "trainingPlansToExercisesExerciseId"))
    @JsonIgnoreProperties("exercise")
    private List<Exercise> exerciseList = new ArrayList<>();
}
