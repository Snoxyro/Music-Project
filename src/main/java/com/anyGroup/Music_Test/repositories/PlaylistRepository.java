package com.anyGroup.Music_Test.repositories;

import com.anyGroup.Music_Test.entities.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
}
