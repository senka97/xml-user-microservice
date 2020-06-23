package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.rabbitmq.Producer;
import com.team19.usermicroservice.rabbitmq.RegistrationMessage;
import com.team19.usermicroservice.repository.ClientRepository;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.repository.VerificationTokenClientRepository;
import com.team19.usermicroservice.model.VerificationTokenClient;
import com.team19.usermicroservice.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private Producer producer;

    @Autowired
    private VerificationTokenClientRepository verificationTokenClientRepository;

    @Autowired
    private VerificationTokenClientServiceImpl verificationTokenClientService;

    @Override
    public RegistrationRequest save(RegistrationRequest registrationRequest) {
        // work factor of bcrypt
        int strength = 10;
        // secureRandom() is salt generator
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        registrationRequest.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
        return registrationRequestRepository.save(registrationRequest);
    }


    @Override
    public List<RegistrationRequest> getAllPendingRegistrationRequests() {
        return registrationRequestRepository.findAllPendingRequests();
    }

    @Override
    public boolean approveRegistrationRequest(Long id) {
        RegistrationRequest registrationRequest = registrationRequestRepository.getOne(id);

        if (registrationRequest != null) {
            registrationRequest.setStatus(RequestStatus.APPROVED);
            registrationRequestRepository.save(registrationRequest);

            RegistrationMessage registrationMessage = new RegistrationMessage();
            registrationMessage.setEmail(registrationRequest.getEmail());

            VerificationTokenClient verificationTokenClient = new VerificationTokenClient(UUID.randomUUID().toString(), registrationRequest);
            verificationTokenClientRepository.save(verificationTokenClient);

            String link = "https://localhost:8080/activate-account?id=" + registrationRequest.getId() + "&token=" + verificationTokenClient.getToken();
            registrationMessage.setContent("Hello. Admin approve your registration request. " +
                    "To activate your account you need to click on this link: " + link + " .For this action you have 24 hours.");
            producer.addRequestToRegistrationQueue("registration-approve-queue", registrationMessage);
            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean rejectRegistrationRequest(Long id) {
        RegistrationRequest registrationRequest = registrationRequestRepository.getOne(id);

        if (registrationRequest != null) {
            registrationRequest.setStatus(RequestStatus.REJECTED);
            registrationRequestRepository.save(registrationRequest);

            RegistrationMessage registrationMessage = new RegistrationMessage();
            registrationMessage.setEmail(registrationRequest.getEmail());
            registrationMessage.setContent("Sorry, but admin rejected your request for registration.");
            producer.addRequestToRegistrationQueue("registration-reject-queue", registrationMessage);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean activateAccount(Long id, String token) {
        RegistrationRequest registrationRequest = registrationRequestRepository.getOne(id);
        VerificationTokenClient verificationTokenClient = verificationTokenClientService.findByToken(token);

        try {
            if (verificationTokenClient != null) {
                Calendar cal = Calendar.getInstance();
                if ((verificationTokenClient.getExpiryDate().getTime() - cal.getTime().getTime()) >= 0) {
                    if (registrationRequest != null) {
                        Client client = new Client();
                        client.setName(registrationRequest.getName());
                        client.setSurname(registrationRequest.getSurname());
                        client.setEmail(registrationRequest.getEmail());
                        client.setPassword(registrationRequest.getPassword());
                        client.setRole("ROLE_CLIENT");
                        client.setEnabled(true);

                        //dodeliti rolu
                        List<Role> role = roleService.findByName("ROLE_CLIENT");
                        client.setRoles(role);

                        client.setPhoneNumber(registrationRequest.getPhoneNumber());
                        client.setPublishedAdsNumber(0);
                        client.setStatus(ClientStatus.ACTIVE);
                        client.setRemoved(false);
                        client.setCanComment(true);
                        clientRepository.save(client);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
