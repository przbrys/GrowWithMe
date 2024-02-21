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
@Table(name = "questions", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "questionId", nullable = false)
    private Integer questionId;
    @Basic
    @Column(name = "questionContent", nullable = false, length = 500)
    private String questionContent;
    @Basic
    @Column(name = "questionClientAnswer", nullable = true, length = 500)
    private String questionClientAnswer;

}
