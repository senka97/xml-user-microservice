package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.VerificationTokenClient;

public interface VerificationTokenClientService {

    VerificationTokenClient findByToken(String token);
}
