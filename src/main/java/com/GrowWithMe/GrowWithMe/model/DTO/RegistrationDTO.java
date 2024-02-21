package com.GrowWithMe.GrowWithMe.model.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationDTO {
    private String userLogin;
    private String userName;
    private String userSurname;
    private String userPassword;
    private String userRole;
}
