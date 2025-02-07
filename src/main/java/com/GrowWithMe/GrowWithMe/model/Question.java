package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "questions", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "questionId", nullable = false)
    private Integer questionId;
    @Basic
    @Column(name = "questionContent", nullable = false)
    private String questionContent;
    @Basic
    @Column(name = "questionClientAnswer", nullable = true)
    private String questionClientAnswer;

    public Question(String questionContent) {
        this.questionContent = questionContent;
        this.questionClientAnswer = "";
    }
}
