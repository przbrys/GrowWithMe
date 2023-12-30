package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "bodyinformations", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BodyInformation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bodyInformationId", nullable = false)
    private int bodyInformationId;
    @Basic
    @Column(name = "bodyInformationWeight", nullable = false)
    private int bodyInformationWeight;
    @Basic
    @Column(name = "bodyInformationBodyFat", nullable = false)
    private int bodyInformationBodyFat;
    @Basic
    @Column(name = "bodyInformationsChestMeasurement", nullable = false)
    private int bodyInformationsChestMeasurement;
    @Basic
    @Column(name = "bodyInformationLegMeasurement", nullable = false)
    private int bodyInformationLegMeasurement;
    @Basic
    @Column(name = "bodyInformationBicepsMeasurement", nullable = false)
    private int bodyInformationBicepsMeasurement;
    @Basic
    @Column(name = "bodyInformationWaistMeasurement", nullable = false)
    private int bodyInformationWaistMeasurement;
    @Basic
    @Column(name = "bodyInformationCalfMeasurement", nullable = false)
    private int bodyInformationCalfMeasurement;
    @Basic
    @Column(name = "bodyTrainerAdditionalInformation", nullable = true, length = 1000)
    private String bodyTrainerAdditionalInformation;

    @ManyToOne
    @JoinColumn(name = "bodyInformationClientId", referencedColumnName = "clientId")
    private Client client;
}
