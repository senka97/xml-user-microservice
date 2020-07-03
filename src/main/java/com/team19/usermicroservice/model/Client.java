package com.team19.usermicroservice.model;

import com.team19.usermicroservice.enumeration.ClientStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "published_ads_number", nullable = true)
    private int publishedAdsNumber;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @Column(name = "removed")
    private boolean removed;

    @Column(name = "can_comment")
    private boolean canComment;

    @Column(name = "canceled_request_number")
    private int canceledRequestNumber;

    @Column(name = "can_add_to_cart")
    private boolean canAddToCart;

    public Client() {

    }

    public Client(String name, String surname, String email, String password, String phoneNumber,
                  int publishedAdsNumber, ClientStatus status, boolean removed) {
        super(name, surname, email, password);
        this.phoneNumber = phoneNumber;
        this.publishedAdsNumber = publishedAdsNumber;
        this.status = status;
        this.removed = false;
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
