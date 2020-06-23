package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    @GetMapping
    @PreAuthorize("hasAuthority('registration_request_read')")
    public ResponseEntity<List<RegistrationRequestDTO>> getAllRegistrationRequests() {

        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllPendingRegistrationRequests();
        List<RegistrationRequestDTO> registrationRequestDTOS = new ArrayList<>();

        for (RegistrationRequest registrationRequest : registrationRequests) {
            registrationRequestDTOS.add(new RegistrationRequestDTO(registrationRequest));
        }

        return new ResponseEntity<>(registrationRequestDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/approve")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> approveRegistrationRequest(@PathVariable Long id) {
        if (registrationRequestService.approveRegistrationRequest(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/reject")
    @PreAuthorize("hasAuthority('registration_request_update')")
    public ResponseEntity<?> rejectRegistrationRequest(@PathVariable Long id) {
        if (registrationRequestService.rejectRegistrationRequest(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}