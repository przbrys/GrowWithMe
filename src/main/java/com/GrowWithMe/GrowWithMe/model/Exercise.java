package com.GrowWithMe.GrowWithMe.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercises", schema = "defaultdb")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Exercise {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "exerciseId", nullable = false)
    private Integer exerciseId;
    @Basic
    @Column(name = "exerciseName", nullable = false)
    private String exerciseName;
    @Basic
    @Column(name = "exerciseNumberOfSeries", nullable = true)
    private Integer exerciseNumberOfSeries;
    @Basic
    @Column(name = "exerciseNumberOfRepetitions", nullable = true)
    private Integer exerciseNumberOfRepetitions;
    @Basic
    @Column(name = "exerciseInformations")
    private String exerciseInformations;


    public Exercise(Exercise exercise){
        this.exerciseName=exercise.getExerciseName();
        this.exerciseNumberOfSeries = exercise.getExerciseNumberOfSeries();
        this.exerciseNumberOfRepetitions = exercise.getExerciseNumberOfRepetitions();
        this.exerciseInformations = exercise.getExerciseInformations();
    }

}
