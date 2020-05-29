package com.team19.usermicroservice.controller;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.dto.CurrentUser;
import com.team19.usermicroservice.model.User;
import com.team19.usermicroservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/currentUser")
    @PreAuthorize("hasAuthority('currentUser_read')")
    public ResponseEntity<?> user() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        System.out.println(email);
        User user = this.userService.getUserByEmail(email);
        CurrentUser currentUserDto = new CurrentUser(user);
        return new ResponseEntity<>(currentUserDto, HttpStatus.OK);
    }

    @PostMapping(value = "/getCommentCreator")
    // treba svi da pristupe jer svi mogu da citaju komentare
    public ArrayList<CommentDTO> getCommentCreator(@RequestBody ArrayList<CommentDTO> comments)
    {
        return this.userService.getCommentCreator(comments);
    }

}
