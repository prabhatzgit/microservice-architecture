package com.pkg.userservice.controller;

import com.pkg.userservice.entities.User;
import com.pkg.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // create user
    @PostMapping("/create/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        // case1: get user from database with help of user repository
        User user = userService.getUser(userId);
        // case1: fetch rating of above user from rating service
        return ResponseEntity.ok(user);
    }

    //get all user
    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}