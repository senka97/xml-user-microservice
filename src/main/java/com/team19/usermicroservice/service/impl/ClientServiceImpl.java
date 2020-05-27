package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.model.Client;
import com.team19.usermicroservice.repository.ClientRepository;
import com.team19.usermicroservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

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
        Client client = clientRepository.getOne(id);
        client.setRemoved(true);
        client.setStatus(ClientStatus.INACTIVE);
        clientRepository.save(client);
    }

    @Override
    public Client activateClient(Long id) {
        Client client = clientRepository.getOne(id);
        client.setStatus(ClientStatus.ACTIVE);
        clientRepository.save(client);
        return client;
    }

    @Override
    public Client blockClient(Long id) {
        Client client = clientRepository.getOne(id);
        client.setStatus(ClientStatus.BLOCKED);
        clientRepository.save(client);
        return client;
    }
}
