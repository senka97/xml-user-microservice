package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.RegistrationRequest;

import java.util.List;

public interface RegistrationRequestService {

    RegistrationRequest save(RegistrationRequest registrationRequest);
    boolean emailExist(String email);
    List<RegistrationRequest> getAllRegistrationRequest();
}
