package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.DTO.LoginRequestDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.LoginResponseDTO;
import com.GrowWithMe.GrowWithMe.model.DTO.RegistrationDTO;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity <User> registerUser(@RequestBody RegistrationDTO registrationDTO){
        Optional<User> userOptional = Optional.ofNullable(authenticationService.registerUser(registrationDTO.getUserLogin(), registrationDTO.getUserName(), registrationDTO.getUserSurname(), registrationDTO.getUserPassword(), registrationDTO.getUserRole()));
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PostMapping("/login")
    public ResponseEntity <LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO){
        Optional<LoginResponseDTO> loginResponseDTOOptional = Optional.ofNullable(authenticationService.loginUser(loginRequestDTO.getUserLogin(), loginRequestDTO.getUserPassword()));
        return loginResponseDTOOptional.map(loginResponseDTO -> new ResponseEntity<>(loginResponseDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
