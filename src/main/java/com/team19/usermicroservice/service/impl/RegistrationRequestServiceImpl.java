package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.repository.UserRepository;
import com.team19.usermicroservice.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RegistrationRequest save(RegistrationRequest registrationRequest) {
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return registrationRequestRepository.save(registrationRequest);
    }

    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        RegistrationRequest registrationRequest = registrationRequestRepository.findByEmail(email);
        if (user != null || registrationRequest != null) {
            return false;
        }
        return true;
    }
}
