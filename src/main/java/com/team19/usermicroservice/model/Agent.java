package com.team19.usermicroservice.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AGENT")
public class Agent extends User {

    @Column(name = "company_name", nullable = true)
    private String companyName;

    @Column(name = "company_number", nullable = true)
    private String companyNumber;

    @Column(name = "address", nullable = true)
    private String address;

    public Agent() {

    }

    public Agent(String name, String surname, String email, String password, String companyName,
                 String companyNumber, String address) {
        super(name, surname, email, password);
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
