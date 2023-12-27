package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "surveys", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Survey {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "surveyId", nullable = false)
    private int surveyId;
    @Basic
    @Column(name = "surveyName", nullable = false, length = 60)
    private String surveyName;
    @Basic
    @Column(name = "surveyClientId", nullable = false)
    private int surveyClientId;

}
