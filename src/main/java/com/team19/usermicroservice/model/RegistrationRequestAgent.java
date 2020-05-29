package com.team19.usermicroservice.model;

import com.team19.usermicroservice.dto.RegistrationRequestAgentDTO;
import com.team19.usermicroservice.enumeration.RequestStatus;

import javax.persistence.*;

@Entity
public class RegistrationRequestAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rreq_name_a", nullable = false)
    private String name;

    @Column(name = "rreq_surname_a", nullable = false)
    private String surname;

    @Column(name = "rreq_email_a", nullable = false, unique = true)
    private String email;

    @Column(name = "rreq_password_a", nullable = false)
    private String password;

    @Column(name = "rreq_company_name_a", nullable = false)
    private String companyName;

    @Column(name = "rreq_company_number_a", nullable = false)
    private String companyNumber;

    @Column(name = "rreq_address_a", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "rreq_status_a", nullable = false)
    private RequestStatus status;

    public RegistrationRequestAgent() {
    }

    public RegistrationRequestAgent(RegistrationRequestAgentDTO registrationRequestAgentDTO) {
        this.id = registrationRequestAgentDTO.getId();
        this.name = registrationRequestAgentDTO.getName();
        this.surname = registrationRequestAgentDTO.getSurname();
        this.email = registrationRequestAgentDTO.getEmail();
        this.password = registrationRequestAgentDTO.getPassword();
        this.companyName = registrationRequestAgentDTO.getCompanyName();
        this.companyNumber = registrationRequestAgentDTO.getCompanyNumber();
        this.address = registrationRequestAgentDTO.getAddress();
        this.status = RequestStatus.PENDING;
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
