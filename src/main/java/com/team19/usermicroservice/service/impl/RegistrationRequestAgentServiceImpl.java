package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.service.RegistrationRequestAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationRequestAgentServiceImpl implements RegistrationRequestAgentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestAgentRepository registrationRequestAgentRepository;

    @Override
    public RegistrationRequestAgent save(RegistrationRequestAgent registrationRequestAgent) {
        registrationRequestAgent.setPassword(passwordEncoder.encode(registrationRequestAgent.getPassword()));
        return registrationRequestAgentRepository.save(registrationRequestAgent);
    }

    @Override
    public List<RegistrationRequestAgent> getAllRegistrationRequestsAgent() {
        return registrationRequestAgentRepository.findAll();
    }
}
