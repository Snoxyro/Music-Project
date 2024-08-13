package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.UserLoginRequest;
import com.anyGroup.Music_Test.dto.UserRegisterRequest;
import com.anyGroup.Music_Test.entities.RoleEntity;
import com.anyGroup.Music_Test.entities.RoleEnum;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.repositories.RoleRepository;
import com.anyGroup.Music_Test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    //Constructor
    @Autowired
    public AuthenticationService(RoleRepository roleRepository, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    //Constructor

    public UserEntity signup(UserRegisterRequest registerRequest) throws ResponseStatusException {
        RoleEntity role = this.roleRepository.findByName(RoleEnum.USER).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role 'USER' not found"));

        if (registerRequest == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request can't be null"); }
        if (registerRequest.getUsername() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username can't be null"); }
        if (registerRequest.getUsername().length() < 5) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is too short"); }
        if (registerRequest.getUsername().length() > 20) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is too long"); }
        if (registerRequest.getEmail() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail can't be null"); }
        if (registerRequest.getEmail().isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail can't be empty"); }
        if (registerRequest.getPassword() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be null"); }
        if (registerRequest.getPassword().length() < 4) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is too short"); }

        UserEntity user = new UserEntity()
                .setUsername(registerRequest.getUsername())
                .setEmail(registerRequest.getEmail())
                .setPassword(this.passwordEncoder.encode(registerRequest.getPassword()))
                .setRole(role);

        return this.userRepository.save(user);
    }

    public UserEntity authenticate(UserLoginRequest loginRequest) {
        if (loginRequest.getUsername() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username can't be null"); }
        if (loginRequest.getUsername().isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username can't be empty"); }
        if (loginRequest.getPassword() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be null"); }
        if (loginRequest.getPassword().isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be empty"); }

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        return this.userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username: '" + loginRequest.getUsername() + "' not found"));
    }
}
