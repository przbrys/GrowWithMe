package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;
import com.GrowWithMe.GrowWithMe.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<User> getUserId(Integer userId);
    void deleteUser(Integer userId);
    User createUserEntity(User user);
    User updateUserPassword(Integer userId, String userNewPassword);
}
