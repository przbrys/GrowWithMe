package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.DTO.LoginRequestDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.LoginResponseDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.RegistrationDTO;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO registrationDTO){
        return authenticationService.registerUser(registrationDTO.getUserLogin(),registrationDTO.getUserName()
                ,registrationDTO.getUserSurname(),registrationDTO.getUserPassword());
    }
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO){
        return authenticationService.loginUser(loginRequestDTO.getUserLogin(),loginRequestDTO.getUserPassword());
    }

}
