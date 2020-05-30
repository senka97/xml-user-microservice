package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.RegistrationRequestAgentRepository;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Override
    public RegistrationRequest save(RegistrationRequest registrationRequest) {
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return registrationRequestRepository.save(registrationRequest);
    }


    @Override
    public List<RegistrationRequest> getAllRegistrationRequests() {
        return registrationRequestRepository.findAll();
    }
}
