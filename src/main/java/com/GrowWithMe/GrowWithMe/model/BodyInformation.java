package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bodyinformations", schema = "mydb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BodyInformation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bodyInformationId", nullable = false)
    private Integer bodyInformationId;
    @Basic
    @Column(name = "bodyInformationWeight", nullable = false)
    private Integer bodyInformationWeight;
    @Basic
    @Column(name = "bodyInformationBodyFat", nullable = false)
    private Integer bodyInformationBodyFat;
    @Basic
    @Column(name = "bodyInformationChestMeasurement", nullable = false)
    private Integer bodyInformationChestMeasurement;
    @Basic
    @Column(name = "bodyInformationLegMeasurement", nullable = false)
    private Integer bodyInformationLegMeasurement;
    @Basic
    @Column(name = "bodyInformationBicepsMeasurement", nullable = false)
    private Integer bodyInformationBicepsMeasurement;
    @Basic
    @Column(name = "bodyInformationWaistMeasurement", nullable = false)
    private Integer bodyInformationWaistMeasurement;
    @Basic
    @Column(name = "bodyInformationCalfMeasurement", nullable = false)
    private Integer bodyInformationCalfMeasurement;
    @Basic
    @Column(name = "bodyTrainerAdditionalInformation", nullable = true, length = 1000)
    private String bodyTrainerAdditionalInformation;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bodyInformationClientId", referencedColumnName = "clientId")
    private Client client;
}
