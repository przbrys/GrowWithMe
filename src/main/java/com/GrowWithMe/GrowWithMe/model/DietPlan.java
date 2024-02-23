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
@Table(name = "dietplans", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DietPlan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dietPlanId", nullable = false)
    private Integer dietPlanId;
    @Basic
    @Column(name = "dietPlanName", nullable = false)
    private String dietPlanName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "dietPlanClientId", referencedColumnName = "clientId")
    private Client client;

    @ManyToMany()
    @JoinTable(name = "mealsToDietPlans",
            joinColumns = @JoinColumn(name = "mealsToDietPlansMealId"),
            inverseJoinColumns = @JoinColumn(name = "mealsToDietPlansDietPlanId"))
    @JsonIgnoreProperties("meal")
    private List<Meal> mealList = new ArrayList<>();
}
