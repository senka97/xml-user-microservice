package com.team19.usermicroservice.service;

import com.team19.usermicroservice.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findByName(String role);
}
