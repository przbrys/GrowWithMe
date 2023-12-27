package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "dietplans", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DietPlan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dietPlanId", nullable = false)
    private int dietPlanId;
    @Basic
    @Column(name = "dietPlanName", nullable = false, length = 100)
    private String dietPlanName;
    @Basic
    @Column(name = "Clients_clientId", nullable = false)
    private int clientsClientId;

}
