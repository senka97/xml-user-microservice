package com.team19.usermicroservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ResetPasswordDTO {

    @NotBlank(message="Password must not be empty.")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{10,}$",
            message="Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and must be minimum 10 characters long")
    private String newPassword;

    private String token;

    public ResetPasswordDTO() {
    }

    public ResetPasswordDTO(String newPassword, String token) {
        this.newPassword = newPassword;
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
