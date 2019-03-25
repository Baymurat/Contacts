package com.itechart.contacts.core.user.dto;

import lombok.Data;

@Data
public class UserDto {

    public UserDto() {}

    private long id;
    private String name;
    private String email;
    private String role;
    private boolean active;
}
