package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.dto.UserLoginRequest;
import com.anyGroup.Music_Test.dto.UserRegisterRequest;
import com.anyGroup.Music_Test.dto.LoginResponse;
import com.anyGroup.Music_Test.services.AuthenticationService;
import com.anyGroup.Music_Test.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    //Constructor
    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    //Constructor

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserEntity registeredUser = authenticationService.signup(userRegisterRequest);

        try { return new ResponseEntity<>(registeredUser, HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginRequest userLoginRequest) {
        UserEntity authenticatedUser = authenticationService.authenticate(userLoginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        try { return new ResponseEntity<>(loginResponse, HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }
}
