package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestAgentDTO;
import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.RegistrationRequestAgent;
import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
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
@RequestMapping(value = "/request/agent", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RegistrationRequestAgentController {

    @Autowired
    private RegistrationRequestAgentServiceImpl registrationRequestAgentService;

    @GetMapping
    @PreAuthorize("hasAuthority('allRegistrationRequestsAgent')")
    public ResponseEntity<List<RegistrationRequestAgentDTO>> getAllRegistrationRequestsAgent() {

        List<RegistrationRequestAgent> registrationRequestsAgent = registrationRequestAgentService.getAllRegistrationRequestsAgent();
        List<RegistrationRequestAgentDTO> registrationRequestAgentDTOS = new ArrayList<>();

        for (RegistrationRequestAgent registrationRequestAgent : registrationRequestsAgent) {
            registrationRequestAgentDTOS.add(new RegistrationRequestAgentDTO(registrationRequestAgent));
        }

        return new ResponseEntity<>(registrationRequestAgentDTOS, HttpStatus.OK);
    }
}
