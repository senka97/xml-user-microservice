package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.RegistrationRequestAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRequestAgentRepository extends JpaRepository<RegistrationRequestAgent, Long> {

    RegistrationRequestAgent findByEmail(String email);

    @Query(value = "SELECT * FROM registration_request_agent  WHERE rreq_status_a = 'PENDING'", nativeQuery = true)
    List<RegistrationRequestAgent> findAllPendingRequests();
}
