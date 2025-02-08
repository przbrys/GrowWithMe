package com.GrowWithMe.GrowWithMe.model.DTO;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDTO {
    private Integer userId;
    private String userName;
    private String userSurname;
    private Integer trainerId;
    private Integer clientId;
    private Trainer trainer;
    private Client client;

    public UserResponseDTO(Integer userId, String userName, String userSurname, Integer trainerId, Integer clientId) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.trainerId = trainerId;
        this.clientId = clientId;
    }

    public UserResponseDTO(Integer userId, String userName, String userSurname, Integer trainerId, Trainer trainer) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.trainerId = trainerId;
        this.trainer = trainer;
    }

    public UserResponseDTO(Integer userId, String userName, String userSurname, Integer clientId, Client client) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.clientId = clientId;
        this.client = client;
    }
}

