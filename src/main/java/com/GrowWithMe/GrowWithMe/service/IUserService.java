package com.GrowWithMe.GrowWithMe.service;
import com.GrowWithMe.GrowWithMe.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<User> getUserById(Integer userId);
    void deleteUserEntity(Integer userId);
    User createUserEntity(User user);
    User updateUserPassword(User userToUpdate);
}
