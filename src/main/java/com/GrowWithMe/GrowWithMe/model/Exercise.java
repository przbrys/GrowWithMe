package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "exercises", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Exercise {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "exerciseId", nullable = false)
    private int exerciseId;
    @Basic
    @Column(name = "exerciseName", nullable = false, length = 45)
    private String exerciseName;
    @Basic
    @Column(name = "exerciseNumberOfSeries", nullable = true)
    private Integer exerciseNumberOfSeries;
    @Basic
    @Column(name = "exerciseNumberOfRepetitions", nullable = true)
    private Integer exerciseNumberOfRepetitions;
    @Basic
    @Column(name = "exercisePhotoURL", nullable = true, length = 300)
    private String exercisePhotoUrl;


}
