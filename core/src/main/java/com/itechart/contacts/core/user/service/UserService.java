package com.itechart.contacts.core.user.service;

import com.itechart.contacts.core.user.entity.ChangePasswordRequest;
import com.itechart.contacts.core.user.entity.SignUpRequest;

public interface UserService {

    int processSignUp(SignUpRequest signUpRequest);

    void processChangePassword(ChangePasswordRequest changePasswordRequest);
}
