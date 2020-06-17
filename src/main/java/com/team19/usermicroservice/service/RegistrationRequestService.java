package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.RegistrationRequest;

import java.util.List;

public interface RegistrationRequestService {

    RegistrationRequest save(RegistrationRequest registrationRequest);
    List<RegistrationRequest> getAllPendingRegistrationRequests();
    boolean approveRegistrationRequest(Long id);
    boolean rejectRegistrationRequest(Long id);
    boolean activateAccount(Long id, String token);
}
