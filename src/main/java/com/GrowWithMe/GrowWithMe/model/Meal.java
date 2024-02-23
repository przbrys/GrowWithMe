package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meals", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Meal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mealId", nullable = false)
    private Integer mealId;
    @Basic
    @Column(name = "mealName", nullable = true)
    private String mealName;
    @Basic
    @Column(name = "mealCaloricValue", nullable = false)
    private Integer mealCaloricValue;
    @Basic
    @Column(name = "mealMacroElements", nullable = false)
    private String mealMacroElements;
    @Basic
    @Column(name = "mealIngredients", nullable = false)
    private String mealIngredients;
    @Basic
    @Column(name = "mealPreparationDescription", nullable = false)
    private String mealPreparationDescription;

    public Meal(Meal meal){
        this.mealName = meal.getMealName();
        this.mealCaloricValue=meal.getMealCaloricValue();
        this.mealMacroElements=meal.getMealMacroElements();
        this.mealIngredients=meal.getMealIngredients();
        this.mealPreparationDescription=meal.getMealPreparationDescription();
    }
}
