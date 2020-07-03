package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.enumeration.ClientStatus;
import com.team19.usermicroservice.model.Client;

public class ClientDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private ClientStatus status;
    private boolean removed;
    private boolean canComment;
    private int canceledRequestNumber;
    private boolean canAddToCart;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String surname, String email, String phoneNumber, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.removed = false;
        this.canComment = true;
        this.canceledRequestNumber = 0;
        this.canAddToCart = true;
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.status = client.getStatus();
        this.removed = client.isRemoved();
        this.canComment = client.isCanComment();
        this.canceledRequestNumber = client.getCanceledRequestNumber();
        this.canAddToCart = client.isCanAddToCart();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public int getCanceledRequestNumber() {
        return canceledRequestNumber;
    }

    public void setCanceledRequestNumber(int canceledRequestNumber) {
        this.canceledRequestNumber = canceledRequestNumber;
    }

    public boolean isCanAddToCart() {
        return canAddToCart;
    }

    public void setCanAddToCart(boolean canAddToCart) {
        this.canAddToCart = canAddToCart;
    }
}
