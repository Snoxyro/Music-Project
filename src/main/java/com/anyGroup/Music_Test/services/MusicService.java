package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.MusicResponse3;
import com.anyGroup.Music_Test.entities.MusicEntity;
import com.anyGroup.Music_Test.repositories.MusicRepository;
import com.anyGroup.Music_Test.serviceImpl.MusicImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MusicService implements MusicImpl {

    private final MusicRepository musicRepository;

    //Constructor
    @Autowired
    public MusicService(MusicRepository musicRepository) { this.musicRepository = musicRepository; }
    //Constructor

    @Override
    public List<MusicResponse3> musicGetAll() {
        List<MusicEntity> musicList = this.musicRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(musicList, new TypeToken<List<MusicResponse3>>() {}.getType());
    }

    @Override
    public MusicResponse3 musicGetById(Long musicId) {
        MusicEntity music = this.musicRepository.findById(musicId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Music not found"));

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(music, MusicResponse3.class);
    }
}
