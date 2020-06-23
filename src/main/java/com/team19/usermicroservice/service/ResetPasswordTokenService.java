package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.ResetPasswordToken;

public interface ResetPasswordTokenService {

    ResetPasswordToken findByToken(String token);
}
