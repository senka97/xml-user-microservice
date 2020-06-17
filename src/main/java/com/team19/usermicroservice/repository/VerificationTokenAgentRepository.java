package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.VerificationTokenAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenAgentRepository extends JpaRepository<VerificationTokenAgent, Long> {

    VerificationTokenAgent findByToken(String token);
}
