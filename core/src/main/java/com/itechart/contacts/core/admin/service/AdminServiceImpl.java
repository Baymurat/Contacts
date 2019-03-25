package com.itechart.contacts.core.admin.service;

import com.itechart.contacts.core.user.dto.UserDto;
import com.itechart.contacts.core.user.entity.User;
import com.itechart.contacts.core.user.repository.UserRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return ObjectMapperUtils.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAllByRole("ROLE_USER", pageable).map(user -> ObjectMapperUtils.map(user, UserDto.class));
    }

    @Transactional
    @Override
    public boolean setUserState(long id, boolean state) {
        User user = userRepository.getOne(id);
        user.setActive(state);
        return true;
    }
}
