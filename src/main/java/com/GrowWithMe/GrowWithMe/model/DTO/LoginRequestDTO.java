package com.GrowWithMe.GrowWithMe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {
    private String userLogin;
    private String userPassword;
}
