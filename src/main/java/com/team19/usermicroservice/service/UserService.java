package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.User;

public interface UserService {

    User getUserByEmail(String username);
}
