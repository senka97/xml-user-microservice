package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.RegistrationRequestDTO;
import com.team19.usermicroservice.dto.UserTokenState;
import com.team19.usermicroservice.model.RegistrationRequest;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.TokenUtils;
import com.team19.usermicroservice.security.auth.JwtAuthenticationRequest;
import com.team19.usermicroservice.service.RegistrationRequestService;
import com.team19.usermicroservice.service.UserService;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    PasswordEncoder  passwordEncoder;

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, IOException {

        if(!checkMail(authenticationRequest.getEmail())) {
            System.out.println("Email not valid");
            return new ResponseEntity<>(new UserTokenState("error",0), HttpStatus.NOT_FOUND);
        }

        User user = userService.getUserByEmail(authenticationRequest.getEmail());

        if(user!=null) {

            if(org.springframework.security.crypto.bcrypt.BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())){
                System.out.println("Logged in successfully, email: " + user.getEmail());
            }else{
                return new ResponseEntity<>(new UserTokenState("error", 0), HttpStatus.OK);
            }

            if(!user.isEnabled())
            {
                System.out.println("User is not activated, email: " + user.getEmail());
                return new ResponseEntity<>(new UserTokenState("notActivated", 0), HttpStatus.OK);
            }

            final Authentication authentication = manager.
                    authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            //ispis permisija
            for (GrantedAuthority authority : authorities) {
                System.out.println("Authority: " + authority.getAuthority());
            }

            User user1 = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user1.getEmail());
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

        }
        else
        {
            System.out.println("User not found");
            return new ResponseEntity<>(new UserTokenState("error", 0), HttpStatus.OK);

        }

    }

    public boolean checkMail(String mail) {

        if(mail.isEmpty()) {
            return false;
        }
        if(mail.contains(";")) {
            return false;
        }
        if(mail.contains(",")) {
            return false;
        }
        for(Character c:mail.toCharArray()) {
            if(Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    /*@PreAuthorize("hasAuthority('loginAgent') or hasAuthority('loginAdmin') or hasAuthority('loginClient')")
    @RequestMapping(value="/logout", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void logOutUser(@Context HttpServletRequest request){

        System.out.println("Logout u auth-service");
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userService.getUserByEmail(email);


        if(user.getRole().equals("ROLE_AGENT"))
        {
            System.out.println("Agent bio ulogovan");
            restTemplate.getForEntity("http://agent/agentSecurity/logout", void.class);

        }else
        {
            System.out.println("Admin ili klijent bio ulogovan");
            restTemplate.getForEntity("http://MegaTravel-XML/api/mainSecurity/logout", void.class);

        }

        SecurityContextHolder.clearContext();
    }*/

    @PostMapping(value = "/registration", consumes = "application/json")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {

        if (!registrationRequestService.emailExist(registrationRequestDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        RegistrationRequest registrationRequest = new RegistrationRequest(registrationRequestDTO);

        if (registrationRequest != null) {
            registrationRequestService.save(registrationRequest);
            return new ResponseEntity<>(registrationRequestDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
