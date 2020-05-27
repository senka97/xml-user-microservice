package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('allRegistrationRequests')")
    public ResponseEntity<List<RegistrationRequestDTO>> getAllRegistrationRequest() {

        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllRegistrationRequest();
        List<RegistrationRequestDTO> registrationRequestDTOS = new ArrayList<>();

        for (RegistrationRequest registrationRequest : registrationRequests) {
            registrationRequestDTOS.add(new RegistrationRequestDTO(registrationRequest));
        }

        return new ResponseEntity<>(registrationRequestDTOS, HttpStatus.OK);
    }
}