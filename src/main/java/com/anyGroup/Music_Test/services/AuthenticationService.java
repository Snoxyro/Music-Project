package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.LoginUserDto;
import com.anyGroup.Music_Test.dto.RegisterUserDto;
import com.anyGroup.Music_Test.entities.RoleEntity;
import com.anyGroup.Music_Test.entities.RoleEnum;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.repositories.RoleRepository;
import com.anyGroup.Music_Test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserEntity signup(RegisterUserDto input) {
        Optional<RoleEntity> optionalRole = this.roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) { return null; }//TODO add exception

        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return this.userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return this.userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
