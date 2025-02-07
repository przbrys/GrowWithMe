package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.DTO.LoginResponseDTO;
import com.GrowWithMe.GrowWithMe.model.Role;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.IRoleRepository;
import com.GrowWithMe.GrowWithMe.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public User registerUser(String userLogin, String userName, String userSurname, String userPassword, String role) {
        try {
            String encodedPassword = passwordEncoder.encode(userPassword);
            Role userRole = roleRepository.findByAuthority(role.toUpperCase()).get();
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);
            return userRepository.save(new User(0, userLogin, userName, userSurname, encodedPassword, authorities));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User with this login already exists.");
        } catch (Exception e) {
            throw new RuntimeException("Error during user registration.");
        }
    }

    public LoginResponseDTO loginUser(String userLogin, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin, password)
            );
            String token = tokenService.generateJWT(auth);
            User user = userRepository.findByUserLogin(userLogin).get();
            Integer userId = user.getUserId();
            return new LoginResponseDTO(user, token, userRepository.findTrainerIdByUserId(userId), userRepository.findClientIdByUserId(userId));
        } catch (BadCredentialsException e) {
            return null;
        }
    }
}
