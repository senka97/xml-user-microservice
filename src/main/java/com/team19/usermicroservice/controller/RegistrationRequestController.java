package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
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
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    Logger logger = LoggerFactory.getLogger(RegistrationRequestController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('registration_request_read')")
    public ResponseEntity<List<RegistrationRequestDTO>> getAllRegistrationRequests() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllPendingRegistrationRequests();
        List<RegistrationRequestDTO> registrationRequestDTOS = new ArrayList<>();

        for (RegistrationRequest registrationRequest : registrationRequests) {
            registrationRequestDTOS.add(new RegistrationRequestDTO(registrationRequest));
        }

        logger.info(MessageFormat.format("RR-read;UserID:{0}", user.getId().toString()));//RR-registration request
        return new ResponseEntity<>(registrationRequestDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/approve")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> approveRegistrationRequest(@PathVariable Long id) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        if (registrationRequestService.approveRegistrationRequest(id)) {
            logger.info(MessageFormat.format("RR-ID:{0}-approved;UserID:{1}", id, user.getId().toString()));//RR-registration request
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info(MessageFormat.format("RR-ID:{0}-not approved;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/reject")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> rejectRegistrationRequest(@PathVariable Long id) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        if (registrationRequestService.rejectRegistrationRequest(id)) {
            logger.info(MessageFormat.format("RR-ID:{0}-rejected;UserID:{1}", id, user.getId().toString()));//RR-registration request
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info(MessageFormat.format("RR-ID:{0}-not rejected;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}