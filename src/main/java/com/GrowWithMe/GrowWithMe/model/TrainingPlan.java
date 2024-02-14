package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

@Entity
@Table(name = "trainingplans", schema = "mydb")
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
    private Integer trainingPlanName;

    @ManyToOne
    @JoinColumn(name = "trainingClientId", referencedColumnName = "clientId")
    private Client client;

    @ManyToMany()
    @JoinTable(name = "trainingPlansToExercises",
            joinColumns = @JoinColumn(name = "trainingPlansToExercisesTrainingPlanId"),
            inverseJoinColumns = @JoinColumn(name = "trainingPlansToExercisesExerciseId"))
    @JsonIgnoreProperties("exercise")
    private List<Exercise> exerciseList = new ArrayList<>();
}
