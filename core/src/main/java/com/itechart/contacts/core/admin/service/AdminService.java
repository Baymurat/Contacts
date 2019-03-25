package com.itechart.contacts.core.admin.service;

import com.itechart.contacts.core.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminService {
    UserDto getUser(long id);

    Page<UserDto> getUsers(Pageable pageable);

    boolean setUserState(long id, boolean state);
}
