package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.ResetPasswordToken;
import com.team19.usermicroservice.repository.ResetPasswordTokenRepository;
import com.team19.usermicroservice.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Override
    public ResetPasswordToken findByToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }
}
