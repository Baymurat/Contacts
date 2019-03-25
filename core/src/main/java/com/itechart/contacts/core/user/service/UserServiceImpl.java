package com.itechart.contacts.core.user.service;

import com.itechart.contacts.core.user.entity.ChangePasswordRequest;
import com.itechart.contacts.core.user.entity.SignUpRequest;
import com.itechart.contacts.core.user.entity.User;
import com.itechart.contacts.core.user.repository.UserRepository;
import com.itechart.contacts.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int processSignUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return Constants.EMAIL_IS_TAKEN;
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("ROLE_USER");
        user.setActive(true);

        userRepository.save(user);

        return Constants.SUCCESS_SIGNUP;
    }

    @Override
    @Transactional
    public void processChangePassword(ChangePasswordRequest changePasswordRequest) {

        User user = userRepository.findByEmail(changePasswordRequest.getUsernameOrEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : ")
                );

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    }
}
