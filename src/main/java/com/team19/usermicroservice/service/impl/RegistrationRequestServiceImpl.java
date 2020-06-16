package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.enumeration.RequestStatusByClient;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.rabbitmq.Producer;
import com.team19.usermicroservice.rabbitmq.RegistrationMessage;
import com.team19.usermicroservice.repository.ClientRepository;
import com.team19.usermicroservice.repository.RegistrationRequestRepository;
import com.team19.usermicroservice.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private Producer producer;

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
            //poslati mejl pa ako klikne odobri kreira se user
            RegistrationMessage registrationMessage = new RegistrationMessage();
            registrationMessage.setEmail(registrationRequest.getEmail());
            String link = "http://localhost:8080/activate/account?id=" + registrationRequest.getId();
            registrationMessage.setContent("Hello. Admin approve your registration request. To activate your account you need to click here: " + link);
            producer.addRequestToQueue("registration-approve-queue", registrationMessage);
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
            //registrationRequest.setStatusByClient(RequestStatusByClient.REJECTED);
            registrationRequestRepository.save(registrationRequest);
            //poslati mejl da je zahtev za registraciju odbijen
            RegistrationMessage registrationMessage = new RegistrationMessage();
            registrationMessage.setEmail(registrationRequest.getEmail());
            registrationMessage.setContent("Sorry, but admin rejected your request for registration.");
            producer.addRequestToQueue("registration-reject-queue", registrationMessage);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void activateAccount(Long id) {
        //ako klijent prihvati onda se on kreira od zahteva
        RegistrationRequest registrationRequest = registrationRequestRepository.getOne(id);

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
            clientRepository.save(client);
//            registrationRequest.setStatusByClient(RequestStatusByClient.APPROVED);
//            registrationRequestRepository.save(registrationRequest);
        }
    }

}
