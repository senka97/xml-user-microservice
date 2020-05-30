package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private RegistrationRequestAgentRepository registrationRequestAgentRepository;

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

    @Override
    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        RegistrationRequest registrationRequest = registrationRequestRepository.findByEmail(email);
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.findByEmail(email);
        if (user != null || registrationRequest != null || registrationRequestAgent != null) {
            return false;
        }
        return true;
    }
}
