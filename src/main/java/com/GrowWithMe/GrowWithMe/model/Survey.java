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
@Table(name = "surveys", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Survey {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "surveyId", nullable = false)
    private Integer surveyId;
    @Basic
    @Column(name = "surveyName", nullable = false, length = 60)
    private String surveyName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "surveyClientId", referencedColumnName = "clientId")
    private Client client;

    @ManyToMany()
    @JoinTable(name = "surveysToQuestions",
            joinColumns = @JoinColumn(name = "surveysToQuestionsSurveyId"),
            inverseJoinColumns = @JoinColumn(name = "surveysToQuestionsQuestionId"))
    @JsonIgnoreProperties("surveyList")
    private List<Question> questionList = new ArrayList<>();
}
