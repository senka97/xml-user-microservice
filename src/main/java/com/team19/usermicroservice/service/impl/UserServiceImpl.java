package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.dto.UserInfoDTO;
import com.team19.usermicroservice.model.*;
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

    @Autowired
    private AgentServiceImpl agentService;

    @Autowired
    private ClientServiceImpl clientService;


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

    @Override
    public UserInfoDTO getUserInfo(Long id) {

        User user = this.userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setName(user.getName());
        userInfoDTO.setSurname(user.getSurname());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setUserId(user.getId());
        userInfoDTO.setRole(user.getRole());
        if(user.getRole().equals("ROLE_AGENT")){
            Agent agent = this.agentService.getAgent(id);
            userInfoDTO.setCompanyName(agent.getCompanyName());
        }
        if(user.getRole().equals("ROLE_CLIENT")){
            Client client = this.clientService.findClient(id);
            userInfoDTO.setPhoneNumber(client.getPhoneNumber());
        }

        return userInfoDTO;
    }
}
