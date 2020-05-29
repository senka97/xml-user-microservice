package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.model.RegistrationRequestAgent;

public class RegistrationRequestAgentDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String companyName;
    private String companyNumber;
    private String address;
    private RequestStatus status;

    public RegistrationRequestAgentDTO() {
    }

    public RegistrationRequestAgentDTO(RegistrationRequestAgent registrationRequestAgent) {
        this.id = registrationRequestAgent.getId();
        this.name = registrationRequestAgent.getName();
        this.surname = registrationRequestAgent.getSurname();
        this.email = registrationRequestAgent.getEmail();
        this.password = registrationRequestAgent.getPassword();
        this.companyName = registrationRequestAgent.getCompanyName();
        this.companyNumber = registrationRequestAgent.getCompanyNumber();
        this.address = registrationRequestAgent.getAddress();
        this.status = registrationRequestAgent.getStatus();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
