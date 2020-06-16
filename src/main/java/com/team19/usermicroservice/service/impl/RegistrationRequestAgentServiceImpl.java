package com.team19.usermicroservice.service.impl;

import com.sun.xml.bind.v2.TODO;
import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.model.Agent;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.rabbitmq.Producer;
import com.team19.usermicroservice.rabbitmq.RegistrationMessage;
import com.team19.usermicroservice.repository.AgentRepository;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.service.RegistrationRequestAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationRequestAgentServiceImpl implements RegistrationRequestAgentService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestAgentRepository registrationRequestAgentRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private Producer producer;

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
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);

        if (registrationRequestAgent != null) {
            registrationRequestAgent.setStatus(RequestStatus.APPROVED);
            registrationRequestAgentRepository.save(registrationRequestAgent);
            RegistrationMessage message = new RegistrationMessage();
            message.setEmail(registrationRequestAgent.getEmail());

            // TODO: dodati link za aktivaciju

            message.setContent("Hello. Admin approve your registration request. To activate your account you need to click here: ");
            producer.addRequestToQueue("registration-approve-queue", message);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean rejectRegistrationRequestAgent(Long id) {
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);

        if (registrationRequestAgent != null) {
            registrationRequestAgent.setStatus(RequestStatus.REJECTED);
            registrationRequestAgentRepository.save(registrationRequestAgent);
            //poslati mejl da je zahtev za registraciju odbijen
            RegistrationMessage message = new RegistrationMessage();
            message.setEmail(registrationRequestAgent.getEmail());
            message.setContent("Sorry, but admin rejected your request for registration.");
            producer.addRequestToQueue("registration-reject-queue", message);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void activateAccountAgent(Long id) {
        RegistrationRequestAgent registrationRequestAgent = registrationRequestAgentRepository.getOne(id);

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
        }
    }
}
