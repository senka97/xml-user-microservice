package com.team19.usermicroservice.rabbitmq;

import java.io.Serializable;

public class RegistrationMessage implements Serializable {

    private String email;
    private String content;

    public RegistrationMessage() {
    }

    public RegistrationMessage(String email, String content) {
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
