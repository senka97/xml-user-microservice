package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.VerificationTokenAgent;

public interface VerificationTokenAgentService {

    VerificationTokenAgent findByToken(String token);
}
