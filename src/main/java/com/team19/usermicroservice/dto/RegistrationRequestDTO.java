package com.team19.usermicroservice.dto;

import com.team19.usermicroservice.model.RegistrationRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationRequestDTO {

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

    @NotBlank(message="Phone number must not be empty.")
    @Pattern(regexp="[0-9]{9,11}$", message="Phone number must contain only digits and must be between 9 and 11 digits")
    private String phoneNumber;

    public RegistrationRequestDTO() {
    }

    public RegistrationRequestDTO(Long id, String name, String surname, String email, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public RegistrationRequestDTO(RegistrationRequest registrationRequest) {
        this.id = registrationRequest.getId();
        this.name = registrationRequest.getName();
        this.surname = registrationRequest.getSurname();
        this.email = registrationRequest.getEmail();
        this.password = registrationRequest.getPassword();
        this.phoneNumber = registrationRequest.getPhoneNumber();
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

}
