package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.UserRegisterRequest;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {

    private final UserService userService;

    //Constructor
    @Autowired
    public AdminController(UserService userService) { this.userService = userService; }
    //Constructor

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') and isAuthenticated()")
    public ResponseEntity<UserEntity> createAdministrator(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserEntity createdAdmin = userService.createAdministrator(userRegisterRequest);

        return ResponseEntity.ok(createdAdmin);
    }
}
