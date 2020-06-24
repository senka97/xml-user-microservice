package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(ActivateAccountController.class);

    @PutMapping(value = "/request/{id}/{token}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id, @PathVariable String token) {
        if (registrationRequestService.activateAccount(id, token)) {
            logger.info("C-activate account;");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("C-activate account failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/agent/request/{id}/{token}")
    public ResponseEntity<?> activateAccountAgent(@PathVariable Long id, @PathVariable String token) {
        if (registrationRequestAgentService.activateAccountAgent(id, token)) {
            logger.info("A-activate account;");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("A-activate account failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
