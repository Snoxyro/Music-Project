package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.MusicResponse3;
import com.anyGroup.Music_Test.serviceImpl.MusicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MusicResponse3>> getAll() { return new ResponseEntity<>(this.musicImpl.musicGetAll(), HttpStatus.OK); }

    @GetMapping("/getById/{musicId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getById(@PathVariable Long musicId) {
        try { return new ResponseEntity<>(this.musicImpl.musicGetById(musicId), HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }
}
