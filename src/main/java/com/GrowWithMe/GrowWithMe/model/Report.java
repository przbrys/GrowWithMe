package com.GrowWithMe.GrowWithMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reports", schema = "defaultdb")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Report {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reportId", nullable = false)
    private Integer reportId;
    @Basic
    @Column(name = "reportClientMessage", nullable = false)
    private String reportClientMessage;
    @Basic
    @Column(name = "reportTrainerMessage", nullable = true)
    private String reportTrainerMessage;
    @Basic
    @Column(name = "reportDate", nullable = false)
    private String reportDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "reportClientId", referencedColumnName = "clientId")
    private Client client;
}
