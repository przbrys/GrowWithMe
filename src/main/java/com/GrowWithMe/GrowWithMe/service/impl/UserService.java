package com.GrowWithMe.GrowWithMe.service.impl;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.repository.IUserRepository;
import com.GrowWithMe.GrowWithMe.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteUserEntity(Integer userId) {
        try {
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e) {
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
        }catch (Exception e){
            throw new RuntimeException("Error in creating new User.",e);
        }
    }

    @Override
    public User updateUserPassword(Integer userId, String userNewPassword) {
        Optional<User> userOptional=userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            user.setUserPassword(userNewPassword);
            return userRepository.save(user);
        }
        else{
            throw new EntityNotFoundException("User entity with id " + userId + " not found");
        }
    }
}
