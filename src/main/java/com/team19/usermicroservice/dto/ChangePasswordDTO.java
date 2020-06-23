package com.team19.usermicroservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ChangePasswordDTO {

    @NotBlank(message="Password must not be empty.")
    private String oldPassword;

    @NotBlank(message="Password must not be empty.")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{10,}$",
            message="Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and must be minimum 10 characters long")
    private String newPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
