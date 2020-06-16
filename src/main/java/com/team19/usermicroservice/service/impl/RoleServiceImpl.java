package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.repository.RoleRepository;
import com.team19.usermicroservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByName(String role) {
        Role auth = roleRepository.findByName(role);
        List<Role> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
