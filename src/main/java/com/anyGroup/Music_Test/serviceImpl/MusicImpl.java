package com.anyGroup.Music_Test.serviceImpl;

import com.anyGroup.Music_Test.dto.MusicResponse3;

import java.util.List;

public interface MusicImpl {

    List<MusicResponse3> musicGetAll();

    MusicResponse3 musicGetById(Long musicId);

}
