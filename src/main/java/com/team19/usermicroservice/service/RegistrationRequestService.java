package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.RegistrationRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface RegistrationRequestService {

    RegistrationRequest save(RegistrationRequest registrationRequest);
    List<RegistrationRequest> getAllPendingRegistrationRequests();
    boolean approveRegistrationRequest(Long id) throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException;
    boolean rejectRegistrationRequest(Long id) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException;
    boolean activateAccount(Long id, String token);
}
