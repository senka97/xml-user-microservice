package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.ClientDTO;
import com.team19.usermicroservice.dto.ClientFrontDTO;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.impl.ClientServiceImpl;
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
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('client_read')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<Client> clients = clientService.getAllClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : clients) {
            clientDTOS.add(new ClientDTO(c));
        }

        logger.info(MessageFormat.format("C-read;UserID:{0}", user.getId().toString())); //C-client
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/active", produces = "application/json")
    @PreAuthorize("hasAuthority('client_read')")
    public ResponseEntity<List<ClientDTO>> getAllActiveClients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<Client> activeClients = clientService.getAllActiveClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : activeClients) {
            clientDTOS.add(new ClientDTO(c));
        }

        logger.info(MessageFormat.format("AC-read;UserID:{0}", user.getId().toString())); //AC-active clients
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/blocked", produces = "application/json")
    @PreAuthorize("hasAuthority('client_read')")
    public ResponseEntity<List<ClientDTO>> getAllBlockedClients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        List<Client> blockedClients = clientService.getAllBlockedClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : blockedClients) {
            clientDTOS.add(new ClientDTO(c));
        }

        logger.info(MessageFormat.format("BC-read;UserID:{0}", user.getId().toString())); //BC-blocked clients
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('client_delete')")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        clientService.removeClient(id);
        logger.info(MessageFormat.format("C-ID:{0}-deleted;UserID:{1}", id, user.getId().toString()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/activate")
    @PreAuthorize("hasAuthority('client_update')")
    public ResponseEntity<ClientDTO> activateClient(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        Client client = clientService.activateClient(id);

        if (client != null) {
            logger.info(MessageFormat.format("C-ID:{0}-activated;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
        }
        logger.info(MessageFormat.format("C-ID:{0}-not activated;UserID:{1}", id, user.getId().toString()));
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasAuthority('client_update')")
    public ResponseEntity<ClientDTO> blockClient(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();

        Client client = clientService.blockClient(id);

        if (client != null) {
            logger.info(MessageFormat.format("C-ID:{0}-blocked;UserID:{1}", id, user.getId().toString()));
            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
        }
        logger.info(MessageFormat.format("C-ID:{0}-not blocked;UserID:{1}", id, user.getId().toString()));
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/fill")
    @PreAuthorize("hasAuthority('fillClient')")
    public List<ClientFrontDTO> fillClientsInformation(@RequestBody List<ClientFrontDTO> clientFrontDTOs){

        return this.clientService.fillClientsInformation(clientFrontDTOs);
    }

    @GetMapping(value = "/{id}")
    public boolean checkClientCanComment(@PathVariable Long id) {
        if (clientService.checkClientCanComment(id)) {
            return true;
        } else {
            return false;
        }
    }

    @PutMapping(value = "/{id}/disable")
    public ResponseEntity<?> disableCreatingComment(@PathVariable Long id) {
        if (clientService.disableCreatingComment(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
