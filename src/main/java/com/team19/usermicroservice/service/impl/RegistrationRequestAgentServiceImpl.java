package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.controller.RegistrationRequestController;
import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.model.*;
import com.team19.usermicroservice.rabbitmq.Producer;
import com.team19.usermicroservice.rabbitmq.RegistrationMessage;
import com.team19.usermicroservice.repository.AgentRepository;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.repository.VerificationTokenAgentRepository;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.RegistrationRequestAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RegistrationRequestAgentServiceImpl implements RegistrationRequestAgentService {

    @Autowired
    private RegistrationRequestAgentRepository registrationRequestAgentRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private Producer producer;

    @Autowired
    private VerificationTokenAgentRepository verificationTokenAgentRepository;

    @Autowired
    private VerificationTokenAgentServiceImpl verificationTokenAgentService;

    Logger logger = LoggerFactory.getLogger(RegistrationRequestAgentServiceImpl.class);

    @Override
    public RegistrationRequestAgent save(RegistrationRequestAgent registrationRequestAgent) {
        // work factor of bcrypt
        int strength = 10;
        // secureRandom() is salt generator
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        registrationRequestAgent.setPassword(bCryptPasswordEncoder.encode(registrationRequestAgent.getPassword()));
        return registrationRequestAgentRepository.save(registrationRequestAgent);
    }

    @Override
    public List<RegistrationRequestAgent> getAllPendingRegistrationRequestsAgent() {
        return registrationRequestAgentRepository.findAllPendingRequests();
    }

    @Override
    public boolean approveRegistrationRequestAgent(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);

        if (registrationRequestAgent != null) {
            registrationRequestAgent.setStatus(RequestStatus.APPROVED);
            registrationRequestAgentRepository.save(registrationRequestAgent);
            RegistrationMessage message = new RegistrationMessage();
            message.setEmail(registrationRequestAgent.getEmail());

            VerificationTokenAgent token = new VerificationTokenAgent(UUID.randomUUID().toString(), registrationRequestAgent);
            logger.info(MessageFormat.format("RRA-ID:{0}-approved,created token", id));
            verificationTokenAgentRepository.save(token);

            String link = "https://localhost:8080/activate-account/agent?id=" + registrationRequestAgent.getId() + "&token=" + token.getToken();
            message.setContent("Hello. Admin approve your registration request. " +
                    "To activate your account you need to click on this link: " + link + " .For this action you have 24 hours.");
            producer.addRequestToRegistrationQueue("registration-approve-queue", message);
            logger.info(MessageFormat.format("RRA-ID:{0}-approved,SRTQ", id)); //SRTQ sending request to queue
            return true;
        } else {
            logger.info(MessageFormat.format("RRA-ID:{0}-not found;UserID:{1}", id, user.getId().toString()));
            return false;
        }
    }

    @Override
    public boolean rejectRegistrationRequestAgent(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);

        if (registrationRequestAgent != null) {
            registrationRequestAgent.setStatus(RequestStatus.REJECTED);
            registrationRequestAgentRepository.save(registrationRequestAgent);

            RegistrationMessage message = new RegistrationMessage();
            message.setEmail(registrationRequestAgent.getEmail());
            message.setContent("Sorry, but admin rejected your request for registration.");
            producer.addRequestToRegistrationQueue("registration-reject-queue", message);
            logger.info(MessageFormat.format("RRA-ID:{0}-rejected,SRTQ", id)); //SRTQ sending request to queue
            return true;
        } else {
            logger.info(MessageFormat.format("RR-ID:{0}-not found;UserID:{1}", id, user.getId().toString()));
            return false;
        }
    }

    @Override
    public boolean activateAccountAgent(Long id, String token) {
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);
        VerificationTokenAgent verificationTokenAgent = verificationTokenAgentService.findByToken(token);

        try {
            if (verificationTokenAgent != null) {
                Calendar cal = Calendar.getInstance();
                if ((verificationTokenAgent.getExpiryDate().getTime() - cal.getTime().getTime()) >= 0) {
                    if (registrationRequestAgent != null) {
                        Agent agent = new Agent();
                        agent.setName(registrationRequestAgent.getName());
                        agent.setSurname(registrationRequestAgent.getSurname());
                        agent.setEmail(registrationRequestAgent.getEmail());
                        agent.setPassword(registrationRequestAgent.getPassword());
                        agent.setRole("ROLE_AGENT");
                        agent.setEnabled(true);

                        //dodeliti rolu
                        List<Role> role = roleService.findByName("ROLE_AGENT");
                        agent.setRoles(role);

                        agent.setCompanyName(registrationRequestAgent.getCompanyName());
                        agent.setCompanyNumber(registrationRequestAgent.getCompanyNumber());
                        agent.setAddress(registrationRequestAgent.getAddress());
                        agentRepository.save(agent);
                        return true;
                    }
                }
                logger.warn(MessageFormat.format("A-activate account;token expired;RRA:ID{0}", id));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("A-activate account failed;RRA:ID{0}", id));
            e.printStackTrace();
        }
        return false;
    }
}
