package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/activate-account", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ActivateAccountController {

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    @Autowired
    private RegistrationRequestAgentServiceImpl registrationRequestAgentService;

    @PutMapping(value = "/request/{id}/{token}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id, @PathVariable String token) {
        if (registrationRequestService.activateAccount(id, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/agent/request/{id}/{token}")
    public ResponseEntity<?> activateAccountAgent(@PathVariable Long id, @PathVariable String token) {
        if (registrationRequestAgentService.activateAccountAgent(id, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
