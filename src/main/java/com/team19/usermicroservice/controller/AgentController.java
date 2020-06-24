package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.AgentDTO;
import com.team19.usermicroservice.model.Agent;
import com.team19.usermicroservice.model.Permission;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.impl.AgentServiceImpl;
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/agent", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AgentController {

    @Autowired
    private AgentServiceImpl agentService;

    Logger logger = LoggerFactory.getLogger(AgentController.class);

    @GetMapping
    @PreAuthorize("hasAuthority('agent_read')")
    public ResponseEntity<List<AgentDTO>> getAllAgents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<Agent> agents = agentService.getAllAgents();
        List<AgentDTO> agentDTOS = new ArrayList<>();

        if (agents == null){
            logger.info(MessageFormat.format("A-not found;UserID:{0}", user.getId().toString()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Agent a : agents) {
            agentDTOS.add(new AgentDTO(a));
        }

        logger.info(MessageFormat.format("A-read;UserID:{0}", user.getId().toString())); //A-agent
        return new ResponseEntity<>(agentDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('agent_read')")
    public ResponseEntity<AgentDTO> getAgent(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        Agent agent = agentService.getAgent(id);

        if (agent != null) {
            logger.info(MessageFormat.format("A-ID:{0}-read;UserID:{1}", id, user.getId().toString())); //A-agent
            return new ResponseEntity<>(new AgentDTO(agent), HttpStatus.OK);
        }

        logger.info(MessageFormat.format("A-ID:{0}-not found;UserID:{1}", id, user.getId().toString()));
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
