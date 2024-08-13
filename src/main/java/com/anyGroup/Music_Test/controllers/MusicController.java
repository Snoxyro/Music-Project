package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.MusicResponse3;
import com.anyGroup.Music_Test.serviceImpl.MusicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/musics")
public class MusicController {

    private final MusicImpl musicImpl;

    //Constructor
    @Autowired
    public MusicController(MusicImpl musicImpl) { this.musicImpl = musicImpl; }
    //Constructor

    @GetMapping("/getAll")
    public List<MusicResponse3> getAll() { return this.musicImpl.musicGetAll(); }

    @GetMapping("/getById/{musicId}")
    public MusicResponse3 getById(@PathVariable Long musicId) { return this.musicImpl.musicGetById(musicId); }

}
