package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.RegistrationRequestAgent;

import java.util.List;

public interface RegistrationRequestAgentService {

    RegistrationRequestAgent save(RegistrationRequestAgent registrationRequestAgent);
    List<RegistrationRequestAgent> getAllRegistrationRequestsAgent();

}
