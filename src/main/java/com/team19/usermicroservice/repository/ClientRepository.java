package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>  {

}
