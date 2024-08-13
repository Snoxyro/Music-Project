package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.ArtistResponse2;
import com.anyGroup.Music_Test.serviceImpl.ArtistImpl;
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
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistImpl artistImpl;

    //Constructor
    @Autowired
    public ArtistController(ArtistImpl artistImpl) { this.artistImpl = artistImpl; }
    //Constructor

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ArtistResponse2>> getAll() { return new ResponseEntity<>(this.artistImpl.artistGetAll(), HttpStatus.OK); }

    @GetMapping("/getById/{artistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getById(@PathVariable Long artistId) {
        try { return new ResponseEntity<>(this.artistImpl.artistGetById(artistId), HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }
}
