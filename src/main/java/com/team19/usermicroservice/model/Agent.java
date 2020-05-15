package com.team19.usermicroservice.model;

public class Agent extends User {

    private String companyName;
    private String companyNumber;
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
