package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.RegistrationRequestAgent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface RegistrationRequestAgentService {

    RegistrationRequestAgent save(RegistrationRequestAgent registrationRequestAgent);
    List<RegistrationRequestAgent> getAllPendingRegistrationRequestsAgent();
    boolean approveRegistrationRequestAgent(Long id) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException;
    boolean rejectRegistrationRequestAgent(Long id) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException;
    boolean activateAccountAgent(Long id, String token);

}
