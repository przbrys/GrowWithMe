package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "meals", schema = "mydb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Meal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mealId", nullable = false)
    private Integer mealId;
    @Basic
    @Column(name = "mealName", nullable = true, length = 100)
    private String mealName;
    @Basic
    @Column(name = "mealCaloricValue", nullable = false)
    private Integer mealCaloricValue;
    @Basic
    @Column(name = "mealMacroElements", nullable = false, length = 100)
    private String mealMacroElements;
    @Basic
    @Column(name = "meallingredients", nullable = false, length = 600)
    private String meallingredients;
    @Basic
    @Column(name = "mealPreparationDescription", nullable = false, length = 1000)
    private String mealPreparationDescription;
    @Basic
    @Column(name = "mealPhotoURL", nullable = true, length = 300)
    private String mealPhotoUrl;
}
