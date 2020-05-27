package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.AgentDTO;
import com.team19.usermicroservice.model.Agent;
import com.team19.usermicroservice.service.impl.AgentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/agent", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AgentController {

    @Autowired
    private AgentServiceImpl agentService;

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('allAgents')")
    public ResponseEntity<List<AgentDTO>> getAllAgents() {

        List<Agent> agents = agentService.getAllAgents();
        List<AgentDTO> agentDTOS = new ArrayList<>();

        if (agents == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Agent a : agents) {
            agentDTOS.add(new AgentDTO(a));
        }

        return new ResponseEntity<>(agentDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('getAgent')")
    public ResponseEntity<AgentDTO> getAgent(@PathVariable Long id) {
        Agent agent = agentService.getAgent(id);

        if (agent != null) {
            return new ResponseEntity<>(new AgentDTO(agent), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
