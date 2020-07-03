package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.client.AdClient;
import com.team19.usermicroservice.client.CarClient;
import com.team19.usermicroservice.client.RentClient;
import com.team19.usermicroservice.dto.ClientFrontDTO;
import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.model.Permission;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.ClientRepository;
import com.team19.usermicroservice.security.auth.TokenBasedAuthentication;
import com.team19.usermicroservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

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
    public void  removeClient(Long id) {
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

        logger.info("RC-calling external services;"); //RC remove client
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

        logger.info("AC-calling external service;");//AC activate client
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

        logger.info("BC-calling external services;");//BC block client
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

    @Override
    public boolean disableCreatingComment(Long clientId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) auth;
        User user = (User) tokenBasedAuthentication.getPrincipal();
        String userID = user.getId().toString();
        String token = tokenBasedAuthentication.getToken();
        String permissions = getPermissions(user);

        List<CommentDTO> rejectedComments = carClient.findAllRejectedComments(clientId, permissions, userID, token);
        Client client = clientRepository.getOne(clientId);

        //ukoliko ima 3 ili vise odbijenih komentara onda, ne moze vise da komentarise ni jedan oglas
        if (rejectedComments.size() >= 3 && client.isCanComment()) {
            client.setCanComment(false);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkClientCanComment(Long id) {
        Client client = clientRepository.getOne(id);
        if(client.isCanComment()) {
            return true;
        }
        return false;
    }

    @Override
    public void changeNumberForCanceledRequests(Long id) {
        Client client = clientRepository.getOne(id);
        int canceledRequest = client.getCanceledRequestNumber() + 1;
        client.setCanceledRequestNumber(canceledRequest);
        clientRepository.save(client);
    }

    @Override
    public boolean disableCreatingReservations(Long clientId) {
        Client client = clientRepository.getOne(clientId);
        if (!checkClientCanAddToCart(clientId)) {
            client.setCanAddToCart(false);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkClientCanAddToCart(Long id) {
        Client client = clientRepository.getOne(id);
        if (client.getCanceledRequestNumber() < 3 && client.isCanAddToCart()) {
            return true;
        }
        return false;
    }
}
