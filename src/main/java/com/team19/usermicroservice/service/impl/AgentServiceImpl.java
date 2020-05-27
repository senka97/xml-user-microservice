package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.Agent;
import com.team19.usermicroservice.repository.AgentRepository;
import com.team19.usermicroservice.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgent(Long id) {
        return agentRepository.getOne(id);
    }
}
