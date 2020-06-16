package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    RegistrationRequest findByEmail(String email);

    @Query(value = "SELECT * FROM registration_request  WHERE rreq_status = 'PENDING'", nativeQuery = true)
    List<RegistrationRequest> findAllPendingRequests();

}
