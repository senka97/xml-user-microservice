package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.enumeration.RequestStatus;
import com.team19.usermicroservice.model.RegistrationRequestAgent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationRequestAgentDTO {

    private Long id;

    @NotBlank(message="Name must not be empty.")
    @Size(min=2, max=20, message = "Name must be from 2 to 20 characters long.")
    @Pattern(regexp="[a-zA-Z ]+$", message="Name must not include special characters and numbers.")
    private String name;

    @NotBlank(message="Surname must not be empty.")
    @Size(min=2, max=20, message = "Surname must be from 2 to 20 characters long.")
    @Pattern(regexp="[a-zA-Z ]+$", message="Surname must not include special characters and numbers.")
    private String surname;

    @NotBlank(message="Email must not be empty.")
    @Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message="Email must contain only letters and numbers.")
    private String email;

    @NotBlank(message="Password must not be empty.")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{10,}$",
            message="Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and must be minimum 10 characters long")
    private String password;

    @NotBlank(message="Company name must not be empty.")
    @Size(min=2, max=20, message = "Company name must be from 2 to 20 characters long.")
    @Pattern(regexp="^[a-zA-Z0-9_ ]*$", message="Company name must not include special characters.")
    private String companyName;

    @NotBlank(message="Company number must not be empty.")
    @Pattern(regexp="[0-9]{9,11}$", message="Company number must contain only digits and must be between 9 and 11 digits")
    private String companyNumber;

    @NotBlank(message="Company address must not be empty.")
    @Size(min=2, max=20, message = "Company address must be from 2 to 20 characters long.")
    @Pattern(regexp="^[a-zA-Z0-9_ ]*$", message="Company name must not include special characters.")
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
