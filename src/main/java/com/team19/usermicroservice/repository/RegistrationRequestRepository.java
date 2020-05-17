package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    RegistrationRequest findByEmail(String email);
}
