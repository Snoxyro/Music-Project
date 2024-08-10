package com.anyGroup.Music_Test.repositories;

import com.anyGroup.Music_Test.entities.RoleEntity;
import com.anyGroup.Music_Test.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(RoleEnum name);

}
