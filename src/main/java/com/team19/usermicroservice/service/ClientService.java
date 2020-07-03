package com.team19.usermicroservice.service;

import com.team19.usermicroservice.dto.ClientFrontDTO;
import com.team19.usermicroservice.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();
    List<Client> getAllActiveClients();
    List<Client> getAllBlockedClients();
    void removeClient(Long id);
    Client activateClient(Long id);
    Client blockClient(Long id);
    List<ClientFrontDTO> fillClientsInformation(List<ClientFrontDTO> clientDrontDTOs);
    Client findClient(Long id);
    boolean disableCreatingComment(Long clientId);
    boolean checkClientCanComment(Long id);
    void changeNumberForCanceledRequests(Long id);
    boolean disableCreatingReservations(Long clientId);
    boolean checkClientCanAddToCart(Long id);

}
