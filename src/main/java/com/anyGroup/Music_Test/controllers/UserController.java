package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.PlaylistRequest;
import com.anyGroup.Music_Test.dto.UserResponse1;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.serviceImpl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserImpl userImpl;

    //Constructor
    @Autowired
    public UserController(UserImpl userImpl) { this.userImpl = userImpl; }
    //Constructor

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<UserResponse1>> allUsers() { return new ResponseEntity<>(userImpl.allUsers(), HttpStatus.OK); }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated() and isFullyAuthenticated()")
    public ResponseEntity<UserResponse1> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return new ResponseEntity<>(userImpl.userGetById(currentUser.getId()), HttpStatus.OK);
    }

    //=============================================================================
    //=============================================================================

    @PostMapping("/me/create_playlist/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addPlaylistById(@RequestBody PlaylistRequest playlistRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        this.userImpl.userCreatePlaylist(currentUser.getId(), playlistRequest);
        try { return new ResponseEntity<>("Playlist '" + playlistRequest.getPlaylistName() + "' has been created successfully", HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }

    @GetMapping("/me/get_all_playlists/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        try { return new ResponseEntity<>(this.userImpl.userGetAllPlaylists(currentUser.getId()), HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }

    @GetMapping("/me/get_playlist_by_id/{playlistId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPlaylistById(@PathVariable Long playlistId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        try { return new ResponseEntity<>(this.userImpl.userGetPlaylistById(currentUser.getId(), playlistId), HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }

    @PostMapping("/me/add_music_to_playlist_by_id/{playlistId}-{musicId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addMusicToUserPlaylistById(@PathVariable Long playlistId, @PathVariable Long musicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        this.userImpl.userAddMusicToPlaylistById(currentUser.getId(), playlistId, musicId);
        try { return new ResponseEntity<>("Music added to playlist successfully", HttpStatus.OK); }
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode()); }
    }
}
