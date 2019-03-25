package com.itechart.contacts.core.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
