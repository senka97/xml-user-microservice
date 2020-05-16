package com.team19.usermicroservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/helloAdmin")
    @PreAuthorize("hasAuthority('helloAdmin')")
    public String helloAdmin()  {
        return "Hello Admin";
    }

    @GetMapping("/helloAgent")
    @PreAuthorize("hasAuthority('helloAgent')")
    public String helloAgent()  {
        return "Hello Agent";
    }

    @GetMapping("/helloClient")
    @PreAuthorize("hasAuthority('helloClient')")
    public String helloClient()  {
        return "Hello Client";
    }

    @PostMapping("/helloAdmin")
    @PreAuthorize("hasAuthority('postAdmin')")
    public String postAdmin()  {
        return "Hello Admin from POST";
    }

    @PostMapping("/helloAgent")
    @PreAuthorize("hasAuthority('postAgent')")
    public String postAgent()  {
        return "Hello Agent from POST";
    }

    @PostMapping("/helloClient")
    @PreAuthorize("hasAuthority('postClient')")
    public String postClient()  {
        return "Hello Client from POST";
    }

}
