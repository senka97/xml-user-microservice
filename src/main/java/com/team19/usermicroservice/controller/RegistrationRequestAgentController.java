package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestAgentDTO;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/agent/request", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestAgentController {

    @Autowired
    private RegistrationRequestAgentServiceImpl registrationRequestAgentService;

    @GetMapping
    @PreAuthorize("hasAuthority('allRegistrationRequestsAgent')")
    public ResponseEntity<List<RegistrationRequestAgentDTO>> getAllRegistrationRequestsAgent() {

        List<RegistrationRequestAgent> registrationRequestsAgent = registrationRequestAgentService.getAllPendingRegistrationRequestsAgent();
        List<RegistrationRequestAgentDTO> registrationRequestAgentDTOS = new ArrayList<>();

        for (RegistrationRequestAgent registrationRequestAgent : registrationRequestsAgent) {
            registrationRequestAgentDTOS.add(new RegistrationRequestAgentDTO(registrationRequestAgent));
        }

        return new ResponseEntity<>(registrationRequestAgentDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/approve")
    //@PreAuthorize("hasAuthority('registration_request_agent_update')")
    public ResponseEntity<?> approveRegistrationRequestAgent(@PathVariable Long id) {
        if (registrationRequestAgentService.approveRegistrationRequestAgent(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/reject")
    //@PreAuthorize("hasAuthority('registration_request_agent_update')")
    public ResponseEntity<?> rejectRegistrationRequestAgent(@PathVariable Long id) {
        if (registrationRequestAgentService.rejectRegistrationRequestAgent(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}/activate/account")
    //@PreAuthorize("hasAuthority('registration_request_agent_update')")
    public ResponseEntity<?> activateAccountAgent(@PathVariable Long id) {
        registrationRequestAgentService.activateAccountAgent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
