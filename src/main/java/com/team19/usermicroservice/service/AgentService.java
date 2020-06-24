package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.Agent;

import java.util.List;

public interface AgentService {

    List<Agent> getAllAgents();
    Agent getAgent(Long id);
}
