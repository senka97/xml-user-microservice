package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.VerificationTokenClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenClientRepository extends JpaRepository<VerificationTokenClient, Long> {

    VerificationTokenClient findByToken(String token);
}
