package com.GrowWithMe.GrowWithMe.service;
import com.GrowWithMe.GrowWithMe.model.DTO.UserResponseDTO;
import com.GrowWithMe.GrowWithMe.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<UserResponseDTO> getUserById(Integer userId);
    void deleteUserEntity(Integer userId);
    User createUserEntity(User user);
    User updateUserPassword(User userToUpdate);
}
