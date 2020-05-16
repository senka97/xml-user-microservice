package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }
}
