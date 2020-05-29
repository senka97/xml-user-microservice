package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public ArrayList<CommentDTO> getCommentCreator(ArrayList<CommentDTO> comments) {

        for(CommentDTO com : comments)
        {
            User u = userRepository.findById(com.getFromComment()).orElse(null);

            if(u != null)
            {
                com.setUserName(u.getName());
                com.setUserLastname(u.getSurname());
            }
        }

        return comments;
    }
}
