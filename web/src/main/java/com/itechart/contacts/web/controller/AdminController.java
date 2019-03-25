package com.itechart.contacts.web.controller;

import com.itechart.contacts.core.admin.service.AdminService;
import com.itechart.contacts.core.user.dto.UserDto;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/about/{id}")
    public UserDto getUser(@PathVariable long id) {
        return adminService.getUser(id);
    }

    @GetMapping("/users")
    public Object getUsers(Pageable pageable) {
        return adminService.getUsers(pageable);
    }

    @PostMapping("/user/changeState/{id}")
    public Object setUserState(@PathVariable long id, @RequestBody boolean state) {
        return adminService.setUserState(id, state) ? Response.SC_OK : Response.SC_INTERNAL_SERVER_ERROR;
    }
}
