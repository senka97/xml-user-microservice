package com.team19.usermicroservice.soap;

import com.rent_a_car.user_service.soap.LoginRequest;
import com.rent_a_car.user_service.soap.LoginResponse;
import com.team19.usermicroservice.dto.UserTokenState;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.security.TokenUtils;
import com.team19.usermicroservice.service.UserService;
import com.team19.usermicroservice.service.impl.RegistrationRequestServiceImpl;
import com.team19.usermicroservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collection;
import java.util.regex.Pattern;

@Endpoint
public class LoginEndpoint {
    private static final String NAMESPACE_URI = "http://www.rent-a-car.com/user-service/soap";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationRequestServiceImpl registrationRequestService;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {

        if(!checkMail(request.getEmail())) {
            System.out.println("Invalid email");
            return null;
        }

        User user = userService.getUserByEmail(request.getEmail());

        if(user!=null) {

            if(org.springframework.security.crypto.bcrypt.BCrypt.checkpw(request.getPassword(), user.getPassword())){
                System.out.println("Logged in successfully, email: " + user.getEmail());
            }else{
                System.out.println("Invalid password");
                return null;
            }

            if(!user.isEnabled())
            {
                System.out.println("User is not activated, email: " + user.getEmail());
                return null;
            }

            final Authentication authentication = manager.
                    authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

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

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setExpiration(expiresIn);
            loginResponse.setToken(jwt);
            return loginResponse;//ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

        }
        else
        {
            System.out.println("User not found");
            return null;
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

}
