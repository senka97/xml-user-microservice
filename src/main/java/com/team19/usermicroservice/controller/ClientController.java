package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.ClientDTO;
import com.team19.usermicroservice.dto.ClientFrontDTO;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('allClients')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {

        List<Client> clients = clientService.getAllClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : clients) {
            clientDTOS.add(new ClientDTO(c));
        }

        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/active", produces = "application/json")
    @PreAuthorize("hasAuthority('allActiveClients')")
    public ResponseEntity<List<ClientDTO>> getAllActiveClients() {

        List<Client> activeClients = clientService.getAllActiveClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : activeClients) {
            clientDTOS.add(new ClientDTO(c));
        }

        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/blocked", produces = "application/json")
    @PreAuthorize("hasAuthority('allBlockedClients')")
    public ResponseEntity<List<ClientDTO>> getAllBlockedClients() {

        List<Client> blockedClients = clientService.getAllBlockedClients();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client c : blockedClients) {
            clientDTOS.add(new ClientDTO(c));
        }

        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('deleteClient')")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.removeClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/activate")
    @PreAuthorize("hasAuthority('activateClient')")
    public ResponseEntity<ClientDTO> activateClient(@PathVariable Long id) {
        Client client = clientService.activateClient(id);

        if (client != null) {
            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasAuthority('blockClient')")
    public ResponseEntity<ClientDTO> blockClient(@PathVariable Long id) {
        Client client = clientService.blockClient(id);

        if (client != null) {
            return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
        }
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
