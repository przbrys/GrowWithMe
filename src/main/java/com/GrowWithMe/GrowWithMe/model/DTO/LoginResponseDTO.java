package com.GrowWithMe.GrowWithMe.model.DTO;

import com.GrowWithMe.GrowWithMe.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class LoginResponseDTO {
    private User user;
    private String jwt;
    private Integer trainerId;
    private Integer clientId;
}
