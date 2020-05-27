package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.model.Agent;

public class AgentDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String companyName;
    private String companyNumber;
    private String address;

    public AgentDTO() {
    }

    public AgentDTO(Long id, String name, String surname, String email, String companyName, String companyNumber, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.address = address;
    }

    public AgentDTO(Agent agent) {
        this.id = agent.getId();
        this.name = agent.getName();
        this.surname = agent.getSurname();
        this.email = agent.getEmail();
        this.companyName = agent.getCompanyName();
        this.companyNumber = agent.getCompanyNumber();
        this.address = agent.getAddress();
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
