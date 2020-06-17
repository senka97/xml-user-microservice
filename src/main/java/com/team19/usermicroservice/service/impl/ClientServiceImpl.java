package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.client.AdClient;
import com.team19.usermicroservice.client.CarClient;
import com.team19.usermicroservice.client.RentClient;
import com.team19.usermicroservice.dto.ClientDTO;
import com.team19.usermicroservice.dto.ClientFrontDTO;
import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.model.Permission;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.ClientRepository;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdClient adClient;

    @Autowired
    private CarClient carClient;

    @Autowired
    private RentClient rentClient;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllActiveClients() {
        List<Client> allClients = getAllClients();
        List<Client> allActiveClients = new ArrayList<>();

        for (Client c : allClients) {
            if (c.getStatus().equals(ClientStatus.ACTIVE)) {
                allActiveClients.add(c);
            }
        }

        return allActiveClients;
    }

    @Override
    public List<Client> getAllBlockedClients() {
        List<Client> allClients = getAllClients();
        List<Client> allBlockedClients = new ArrayList<>();

        for (Client c : allClients) {
            if (c.getStatus().equals(ClientStatus.BLOCKED)) {
                allBlockedClients.add(c);
            }
        }

        return allBlockedClients;
    }

    @Override
    public void removeClient(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        String userID = user.getId().toString();
        String token = tokenBasedAuthentication.getToken();
        String permissions = getPermissions(user);

        Client client = clientRepository.getOne(id);
        client.setRemoved(true);
        client.setStatus(ClientStatus.INACTIVE);
        client.setEnabled(false);

        adClient.hideAdsForBlockedClient(id, permissions, userID, token);
        carClient.hideCommentRequestsForBlockedAndRemovedClient(id, permissions, userID, token);
        rentClient.rejectAllPendingRequestForBlockedOrRemovedClient(id, permissions, userID, token);
        clientRepository.save(client);
    }

    public String getPermissions(User user) {
        String permissions = "";
        for(Role role: user.getRoles()){
            for(Permission per: role.getPermission()){
                permissions += per.getName() + "|";
            }
        }
        permissions = permissions.substring(0, permissions.length() - 1);
        return permissions;
    }

    @Override
    public Client activateClient(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        String userID = user.getId().toString();
        String token = tokenBasedAuthentication.getToken();
        String permissions = getPermissions(user);

        Client client = clientRepository.getOne(id);
        client.setStatus(ClientStatus.ACTIVE);
        client.setEnabled(true);

        adClient.showAdsForActiveClient(id, permissions, userID, token);
        clientRepository.save(client);
        return client;

    }

    @Override
    public Client blockClient(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        String userID = user.getId().toString();
        String token = tokenBasedAuthentication.getToken();
        String permissions = getPermissions(user);

        Client client = clientRepository.getOne(id);
        client.setStatus(ClientStatus.BLOCKED);
        client.setEnabled(false);

        adClient.hideAdsForBlockedClient(id, permissions, userID, token);
        carClient.hideCommentRequestsForBlockedAndRemovedClient(id, permissions, userID, token);
        rentClient.rejectAllPendingRequestForBlockedOrRemovedClient(id, permissions, userID, token);
        clientRepository.save(client);
        return client;

    }

    @Override
    public List<ClientFrontDTO> fillClientsInformation(List<ClientFrontDTO> clientFrontDTOs) {

        for(ClientFrontDTO c: clientFrontDTOs){
            Client client = clientRepository.findById(c.getId()).orElse(null);
            if(client != null){
                c.setName(client.getName());
                c.setSurname(client.getSurname());
                c.setEmail(client.getEmail());
            }
        }
        return clientFrontDTOs;
    }

    @Override
    public Client findClient(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }
}
