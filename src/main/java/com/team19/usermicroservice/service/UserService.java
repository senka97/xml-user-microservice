package com.team19.usermicroservice.service;

import com.team19.usermicroservice.dto.CommentDTO;
import com.team19.usermicroservice.dto.ForgotPasswordDTO;
import com.team19.usermicroservice.dto.ResetPasswordDTO;
import com.team19.usermicroservice.dto.UserInfoDTO;
import com.team19.usermicroservice.model.User;

import java.util.ArrayList;

public interface UserService {

    User getUserByEmail(String username);
    ArrayList<CommentDTO> getCommentCreator(ArrayList<CommentDTO> comments);
    boolean emailExist(String email);
    UserInfoDTO getUserInfo(Long id);
    boolean forgotPassword(ForgotPasswordDTO forgotPasswordDTO);
    boolean resetPassword(ResetPasswordDTO resetPasswordDTO);
}
