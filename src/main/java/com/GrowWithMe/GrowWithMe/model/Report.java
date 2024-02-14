package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reports", schema = "mydb")
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
    @Column(name = "reportClientMessage", nullable = false, length = 400)
    private String reportClientMessage;
    @Basic
    @Column(name = "reportTrainerMessage", nullable = true, length = 400)
    private String reportTrainerMessage;
    @Basic
    @Column(name = "reportDate", nullable = false)
    private Date reportDate;

    @ManyToOne
    @JoinColumn(name = "reportClientId", referencedColumnName = "clientId")
    private Client client;
}
