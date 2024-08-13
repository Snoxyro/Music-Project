package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.ArtistResponse2;
import com.anyGroup.Music_Test.serviceImpl.ArtistImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistImpl artistImpl;

    //Constructor
    @Autowired
    public ArtistController(ArtistImpl artistImpl) { this.artistImpl = artistImpl; }
    //Constructor

    @GetMapping("/getAll")
    public List<ArtistResponse2> getAll() { return this.artistImpl.artistGetAll(); }

    @GetMapping("/getById/{artistId}")
    public ArtistResponse2 getById(@PathVariable Long artistId) { return this.artistImpl.artistGetById(artistId); }
}
