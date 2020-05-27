package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();
    List<Client> getAllActiveClients();
    List<Client> getAllBlockedClients();
    void removeClient(Long id);
    Client activateClient(Long id);
    Client blockClient(Long id);

}
