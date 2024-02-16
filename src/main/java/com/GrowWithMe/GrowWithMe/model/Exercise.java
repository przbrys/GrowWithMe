package com.GrowWithMe.GrowWithMe.model;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "exercises", schema = "mydb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Exercise {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "exerciseId", nullable = false)
    private Integer exerciseId;
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
    @Column(name = "exerciseInformations", length = 1000)
    private String exerciseInformations;
    @Basic
    @Column(name = "exercisePhotoURL", nullable = true, length = 300)
    private String exercisePhotoUrl;

}
