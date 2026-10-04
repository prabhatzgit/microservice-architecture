package com.pkg.userservice.service;

import com.pkg.userservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // create User
    User saveUser(User user);

    // get all user
    List<User> getAllUser();

    // get Single user of given id
    User getUser(String id);
}