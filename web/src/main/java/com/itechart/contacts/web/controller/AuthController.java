package com.itechart.contacts.web.controller;

import com.itechart.contacts.core.user.entity.ChangePasswordRequest;
import com.itechart.contacts.core.user.service.UserService;
import com.itechart.contacts.core.utils.Constants;
import com.itechart.contacts.web.payload.ApiResponse;
import com.itechart.contacts.core.user.entity.SignUpRequest;
import com.itechart.contacts.web.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        int result = userService.processSignUp(signUpRequest);

        if (result == Constants.EMAIL_IS_TAKEN) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/changePassword")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.processChangePassword(changePasswordRequest);
    }
}
