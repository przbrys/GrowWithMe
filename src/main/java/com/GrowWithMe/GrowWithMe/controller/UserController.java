package com.GrowWithMe.GrowWithMe.controller;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.service.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList=userService.getAllUser();
        return new ResponseEntity<List<User>>(userList, userList.isEmpty()? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        Optional<User> userOptional=userService.getUserId(id);
        return userOptional.map(user -> new ResponseEntity<>(user,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUserEntity(@RequestBody User user) {
        User createdNewUser = userService.createUserEntity(user);
        return new ResponseEntity<>(createdNewUser,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable Integer id)
    {
        try {
            userService.deleteUserEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/newPassword")
    public ResponseEntity<User> updateUserPassword(@PathVariable Integer id,@RequestBody User userWithNewPassword){
        try {
            User userUpdated=userService.updateUserPassword(id,userWithNewPassword.getUserPassword());
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
