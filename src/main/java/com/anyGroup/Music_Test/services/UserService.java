package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.RegisterUserDto;
import com.anyGroup.Music_Test.entities.RoleEntity;
import com.anyGroup.Music_Test.entities.RoleEnum;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.repositories.RoleRepository;
import com.anyGroup.Music_Test.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> allUsers() {
        return new ArrayList<>(this.userRepository.findAll());
    }

    public UserEntity createAdministrator(RegisterUserDto input) {
        Optional<RoleEntity> optionalRole = this.roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return this.userRepository.save(user);
    }
}
