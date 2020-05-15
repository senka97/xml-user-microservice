package com.team19.usermicroservice.model;

import com.team19.usermicroservice.enumeration.ClientStatus;

public class Client extends User {

    private String phoneNumber;
    private int publishedAdsNumber;
    private ClientStatus status;

    public Client() {

    }

    public Client(String name, String surname, String email, String password, String phoneNumber,
                  int publishedAdsNumber, ClientStatus status) {
        super(name, surname, email, password);
        this.phoneNumber = phoneNumber;
        this.publishedAdsNumber = publishedAdsNumber;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPublishedAdsNumber() {
        return publishedAdsNumber;
    }

    public void setPublishedAdsNumber(int publishedAdsNumber) {
        this.publishedAdsNumber = publishedAdsNumber;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }
}
