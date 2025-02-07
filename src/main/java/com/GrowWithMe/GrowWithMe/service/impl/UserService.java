package com.GrowWithMe.GrowWithMe.service.impl;

import com.GrowWithMe.GrowWithMe.model.DTO.UserResponseDTO;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.IClientRepository;
import com.GrowWithMe.GrowWithMe.repository.ITrainerRepository;
import com.GrowWithMe.GrowWithMe.repository.IUserRepository;
import com.GrowWithMe.GrowWithMe.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private ITrainerRepository trainerRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Integer userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Integer trainerId = userRepository.findTrainerIdByUserId(user.getUserId());
            Integer clientId = userRepository.findClientIdByUserId(user.getUserId());

            if (trainerId == null && clientId == null) {
                UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUserId(), user.getUserName(), user.getUserSurname(), trainerId, clientId);
                return Optional.of(userResponseDTO);
            } else if (trainerId == null) {
                UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUserId(), user.getUserName(), user.getUserSurname(), clientId, clientRepository.findById(clientId).get());
                return Optional.of(userResponseDTO);
            } else if (clientId == null) {
                UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUserId(), user.getUserName(), user.getUserSurname()
                        , trainerId, trainerRepository.findById(trainerId).get());
                return Optional.of(userResponseDTO);
            }
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUserId(), user.getUserName(), user.getUserSurname(), trainerId, clientId, trainerRepository.findById(trainerId).get(), clientRepository.findById(clientId).get());
            return Optional.of(userResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserEntity(Integer userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User entity with id " + userId + " not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting User", e);
        }
    }

    @Override
    public User createUserEntity(User user) {
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error in creating new User.", e);
        }
    }

    @Override
    public User updateUserPassword(User userToUpdate) {
        Optional<User> userOptional = userRepository.findById(userToUpdate.getUserId());
        if (userOptional.isPresent()) {
            passwordEncoder.encode(userToUpdate.getPassword());
            return userRepository.save(userToUpdate);
        } else {
            throw new EntityNotFoundException("User entity with id " + userToUpdate.getUserId() + " not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserLogin(username).orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }
}
