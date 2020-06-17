package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.VerificationTokenAgent;
import com.team19.usermicroservice.repository.VerificationTokenAgentRepository;
import com.team19.usermicroservice.service.VerificationTokenAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenAgentServiceImpl implements VerificationTokenAgentService {

    @Autowired
    private VerificationTokenAgentRepository verificationTokenAgentRepository;

    @Override
    public VerificationTokenAgent findByToken(String token) {
        return verificationTokenAgentRepository.findByToken(token);
    }
}
