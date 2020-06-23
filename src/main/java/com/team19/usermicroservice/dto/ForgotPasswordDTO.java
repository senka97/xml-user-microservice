package com.team19.usermicroservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ForgotPasswordDTO {

    @NotBlank(message="Email must not be empty.")
    @Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message="Email must contain only letters and numbers.")
    private String email;

    public ForgotPasswordDTO() {
    }

    public ForgotPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
