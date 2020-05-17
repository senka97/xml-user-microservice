package com.team19.usermicroservice.model;

import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.enumeration.RequestStatus;

import javax.persistence.*;


@Entity
public class RegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rreq_name", nullable = false)
    private String name;

    @Column(name = "rreq_surname", nullable = false)
    private String surname;

    @Column(name = "rreq_email", nullable = false, unique = true)
    private String email;

    @Column(name = "rreq_password", nullable = false)
    private String password;

    @Column(name = "rreq_phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "rreq_status", nullable = false)
    private RequestStatus status;

    public RegistrationRequest() {
    }

    public RegistrationRequest(Long id, String name, String surname, String email, String password,
                               String phoneNumber, RequestStatus status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public RegistrationRequest(RegistrationRequestDTO registrationRequestDTO) {
        this.id = registrationRequestDTO.getId();
        this.name = registrationRequestDTO.getName();
        this.surname = registrationRequestDTO.getSurname();
        this.email = registrationRequestDTO.getEmail();
        this.password = registrationRequestDTO.getPassword();
        this.phoneNumber = registrationRequestDTO.getPhoneNumber();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
