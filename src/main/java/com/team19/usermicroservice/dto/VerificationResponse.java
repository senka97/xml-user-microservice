package com.team19.usermicroservice.dto;

public class VerificationResponse {

    String permissions;
    String userID;

    public VerificationResponse(String permissions, String userID){
        this.permissions = permissions;
        this.userID = userID;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
