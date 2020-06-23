package com.team19.usermicroservice.rabbitmq;

import java.io.Serializable;

public class Message implements Serializable {

    private String email;
    private String subject;
    private String content;

    public Message() {
    }

    public Message(String email, String subject, String content) {
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
