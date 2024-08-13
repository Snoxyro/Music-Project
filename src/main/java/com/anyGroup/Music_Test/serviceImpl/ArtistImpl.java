package com.anyGroup.Music_Test.serviceImpl;

import com.anyGroup.Music_Test.dto.ArtistResponse2;
import com.anyGroup.Music_Test.entities.ArtistEntity;

import java.util.List;

public interface ArtistImpl {

    List<ArtistResponse2> artistGetAll();

    ArtistResponse2 artistGetById(Long artistId);

}
