package com.GrowWithMe.GrowWithMe.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "clients", schema = "mydb", catalog = "")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clientId", nullable = false)
    private int clientId;
    @Basic
    @Column(name = "clientUserId", nullable = false)
    private int clientUserId;
    @Basic
    @Column(name = "clientTtrainerId", nullable = false)
    private int clientTtrainerId;
    @Basic
    @Column(name = "clientTrainerId", nullable = false)
    private int clientTrainerId;
    @Basic
    @Column(name = "clientHeight", nullable = false)
    private int clientHeight;
    @Basic
    @Column(name = "clientPhoneNumber", nullable = true)
    private Integer clientPhoneNumber;

}
