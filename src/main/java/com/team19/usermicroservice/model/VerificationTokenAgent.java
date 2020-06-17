package com.team19.usermicroservice.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationTokenAgent {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_request_agent_id", referencedColumnName = "id")
    private RegistrationRequestAgent registrationRequestAgent;

    @Column(name = "date", nullable = false)
    private Date expiryDate;

    public VerificationTokenAgent() {
    }

    public VerificationTokenAgent(String token, RegistrationRequestAgent registrationRequestAgent) {
        this.token = token;
        this.registrationRequestAgent = registrationRequestAgent;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegistrationRequestAgent getRegistrationRequestAgent() {
        return registrationRequestAgent;
    }

    public void setRegistrationRequestAgent(RegistrationRequestAgent registrationRequestAgent) {
        this.registrationRequestAgent = registrationRequestAgent;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
