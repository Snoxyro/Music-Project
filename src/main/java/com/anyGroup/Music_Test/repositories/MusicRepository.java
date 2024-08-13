package com.anyGroup.Music_Test.repositories;

import com.anyGroup.Music_Test.entities.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
}
