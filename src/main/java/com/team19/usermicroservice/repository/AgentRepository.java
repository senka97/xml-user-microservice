package com.team19.usermicroservice.repository;

import com.team19.usermicroservice.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
