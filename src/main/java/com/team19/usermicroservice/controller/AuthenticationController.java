package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.*;
import com.team19.usermicroservice.model.*;
import com.team19.usermicroservice.security.TokenUtils;
import com.team19.usermicroservice.security.auth.JwtAuthenticationRequest;
import com.team19.usermicroservice.service.UserService;
import com.team19.usermicroservice.service.impl.CustomUserDetailsService;
import com.team19.usermicroservice.service.impl.RegistrationRequestAgentServiceImpl;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;

import com.team19.usermicroservice.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

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

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RegistrationRequestAgentServiceImpl registrationRequestAgentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, IOException {

        if(!checkMail(authenticationRequest.getEmail())) {
            logger.warn("Logging in - invalid email");
            return new ResponseEntity<>("Invalid email",HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByEmail(authenticationRequest.getEmail());

        if(user!=null) {

            if(org.springframework.security.crypto.bcrypt.BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())){
                //System.out.println("Logged in successfully, email: " + user.getEmail());
            }else{
                logger.warn("Logging in - invalid password");
                return new ResponseEntity<>("Invalid password",HttpStatus.BAD_REQUEST);
            }

            if(!user.isEnabled())
            {
                logger.warn("Logging in - user not activated, email: " + user.getEmail());
                return new ResponseEntity<>("User not activated",HttpStatus.EXPECTATION_FAILED);
            }

            final Authentication authentication = manager.
                    authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            //ispis permisija
           /* for (GrantedAuthority authority : authorities) {
                System.out.println("Authority: " + authority.getAuthority());
            }*/

            User user1 = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user1.getEmail());
            int expiresIn = tokenUtils.getExpiredIn();

            logger.info("Logging in - successful, email: " + user.getEmail());
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

        }
        else
        {
            logger.warn("Login failed:user not found");
            return new ResponseEntity<>("User doesn't exist in the system",HttpStatus.NOT_FOUND);
        }

    }

    public boolean checkMail(String mail) {

        if(!Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", mail)){
            return false;
        }

        if(mail.isEmpty()) {
            return false;
        }

        return true;
    }


    @RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
    public VerificationResponse verify(@RequestBody  String token){
        System.out.println("Uslo u verifikaciju");
        System.out.println("Token: " + token);
        String email = tokenUtils.getEmailFromToken(token);
        System.out.println("Email: " + email);
        User user = userService.getUserByEmail(email);
        String permissions = "";
        String userID = user.getId().toString();
        if(tokenUtils.validateToken(token,user)){
            System.out.println("Token validan");
            for(Role role: user.getRoles()){
                for(Permission per: role.getPermission()){
                    permissions += per.getName() + "|";
                }
            }
            permissions = permissions.substring(0, permissions.length() - 1); //uklonim poslednju |
            System.out.println("Permisije " + permissions);
            VerificationResponse vr = new VerificationResponse(permissions,userID);
            return vr;
        }else{
            return null;
        }

    }

    @PostMapping(value = "/registration", consumes = "application/json")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {

        if (!userServiceImpl.emailExist(registrationRequestDTO.getEmail())) {
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

    @PostMapping(value = "/registration/agent", consumes = "application/json")
    public ResponseEntity<?> registrationAgent(@Valid @RequestBody RegistrationRequestAgentDTO registrationRequestAgentDTO) {

        if (!userServiceImpl.emailExist(registrationRequestAgentDTO.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        RegistrationRequestAgent registrationRequestAgent = new RegistrationRequestAgent(registrationRequestAgentDTO);

        if (registrationRequestAgent != null) {
            registrationRequestAgentService.save(registrationRequestAgent);
            return new ResponseEntity<>(registrationRequestAgentDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasAuthority('password_update')")
    @PostMapping(value = "/change-password", consumes = "application/json")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        customUserDetailsService.changePassword(changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password", consumes = "application/json")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        if (userServiceImpl.forgotPassword(forgotPasswordDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/reset-password", consumes = "application/json")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        if (userServiceImpl.resetPassword(resetPasswordDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
