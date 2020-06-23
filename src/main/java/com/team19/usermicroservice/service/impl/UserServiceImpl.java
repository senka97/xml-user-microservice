package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.dto.ForgotPasswordDTO;
import com.team19.usermicroservice.dto.ResetPasswordDTO;
import com.team19.usermicroservice.dto.UserInfoDTO;
import com.team19.usermicroservice.model.*;
import com.team19.usermicroservice.rabbitmq.Message;
import com.team19.usermicroservice.rabbitmq.Producer;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.repository.ResetPasswordTokenRepository;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

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

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private Producer producer;

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

    @Override
    public boolean forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
        if (user != null) {
            Message message = new Message();
            message.setEmail(user.getEmail());

            ResetPasswordToken resetPasswordToken = new ResetPasswordToken(UUID.randomUUID().toString(), user);
            resetPasswordTokenRepository.save(resetPasswordToken);

            String link = "http://localhost:8080/reset-password?token=" + resetPasswordToken.getToken();
            message.setSubject("Resetting password");
            message.setContent("Hello. To reset your password you need to click on this link: " + link + " .For this action you have 24 hours.");
            producer.addRequestToMessageQueue("message-queue", message);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(resetPasswordDTO.getToken());
        try {
            if (resetPasswordToken != null) {
                Calendar cal = Calendar.getInstance();
                if ((resetPasswordToken.getExpiryDate().getTime() - cal.getTime().getTime()) >= 0) {
                    User user = resetPasswordToken.getUser();
                    if (user != null) {
                        // work factor of bcrypt
                        int strength = 10;
                        // secureRandom() is salt generator
                        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
                        user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDTO.getNewPassword()));
                        userRepository.save(user);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
