package com.team19.usermicroservice.service.impl;

import com.team19.usermicroservice.model.Permission;
import com.team19.usermicroservice.model.Role;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email)  throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return user;
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getGrantedAuthorities(getPermissions(role));
    }

    private Collection<Permission> getPermissions(Role role) {
        return role.getPermission();
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Permission> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission p : permissions) {
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        }
        return authorities;
    }

    // Funkcija pomocu koje korisnik menja svoju lozinku
  /* public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();

        if (authenticationManager != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, oldPassword));
        } else {
            return;
        }

        User user = (User) loadUserByUsername(email);

        user.setPassword(passwordEncoder.encode(newPassword));
        //user.setPasswordChanged(true);
        userRepository.save(user);

    }*/

}
