package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.repository.VerificationTokenClientRepository;
import com.team19.usermicroservice.model.VerificationTokenClient;
import com.team19.usermicroservice.service.VerificationTokenClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenClientServiceImpl implements VerificationTokenClientService {

    @Autowired
    private VerificationTokenClientRepository verificationTokenClientRepository;

    @Override
    public VerificationTokenClient findByToken(String token) {
        return verificationTokenClientRepository.findByToken(token);
    }
}
