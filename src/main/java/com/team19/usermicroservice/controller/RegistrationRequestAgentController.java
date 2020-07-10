package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestAgentDTO;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/agent/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestAgentController {

    @Autowired
    private RegistrationRequestAgentServiceImpl registrationRequestAgentService;

    Logger logger = LoggerFactory.getLogger(RegistrationRequestAgentController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('registration_request_a_read')")
    public ResponseEntity<List<RegistrationRequestAgentDTO>> getAllRegistrationRequestsAgent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<RegistrationRequestAgent> registrationRequestsAgent = registrationRequestAgentService.getAllPendingRegistrationRequestsAgent();
        List<RegistrationRequestAgentDTO> registrationRequestAgentDTOS = new ArrayList<>();

        for (RegistrationRequestAgent registrationRequestAgent : registrationRequestsAgent) {
            registrationRequestAgentDTOS.add(new RegistrationRequestAgentDTO(registrationRequestAgent));
        }

        logger.info(MessageFormat.format("RRA-read;UserID:{0}", user.getId().toString()));//RRA-registration request agent
        return new ResponseEntity<>(registrationRequestAgentDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/approve")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> approveRegistrationRequestAgent(@PathVariable Long id) throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        if (registrationRequestAgentService.approveRegistrationRequestAgent(id)) {
            logger.info(MessageFormat.format("RRA-ID:{0}-approved;UserID:{1}", id, user.getId().toString()));//RRA-registration request agent
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info(MessageFormat.format("RRA-ID:{0}-not approved;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/reject")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> rejectRegistrationRequestAgent(@PathVariable Long id) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        if (registrationRequestAgentService.rejectRegistrationRequestAgent(id)) {
            logger.info(MessageFormat.format("RRA-ID:{0}-rejected;UserID:{1}", id, user.getId().toString()));//RRA-registration request agent
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info(MessageFormat.format("RRA-ID:{0}-not rejected;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
