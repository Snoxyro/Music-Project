package com.anyGroup.Music_Test.controllers;

import com.anyGroup.Music_Test.dto.PlaylistRequest;
import com.anyGroup.Music_Test.dto.PlaylistResponse1;
import com.anyGroup.Music_Test.dto.UserResponse1;
import com.anyGroup.Music_Test.entities.UserEntity;
import com.anyGroup.Music_Test.serviceImpl.UserImpl;
import com.anyGroup.Music_Test.services.JwtService;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserImpl userImpl;

    private final JwtService jwtService;

    //Constructor
    @Autowired
    public UserController(UserImpl userImpl, JwtService jwtService) { this.userImpl = userImpl; this.jwtService = jwtService; }
    //Constructor

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<UserResponse1> allUsers() { return userImpl.allUsers(); }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponse1 authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return userImpl.userGetById(currentUser.getId());
    }

    //=============================================================================
    //=============================================================================

    @PostMapping("/me/create_playlist/")
    public String addPlaylistById(@RequestBody PlaylistRequest playlistRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        this.userImpl.userCreatePlaylist(currentUser.getId(), playlistRequest);
        return "Playlist '" + playlistRequest.getPlaylistName() + "' has been created successfully";
    }

    @GetMapping("/me/get_all_playlists/")
    public List<PlaylistResponse1> getAllPlaylists() throws ResponseStatusException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return this.userImpl.userGetAllPlaylists(currentUser.getId());
    }

    @GetMapping("/me/get_playlist_by_id/{playlistId}")
    public PlaylistResponse1 getPlaylistById(@PathVariable Long playlistId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return this.userImpl.userGetPlaylistById(currentUser.getId(), playlistId);
    }

    @PostMapping("/me/add_music_to_playlist_by_id/{playlistId}-{musicId}")
    public String addMusicToUserPlaylistById(@PathVariable Long playlistId, @PathVariable Long musicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        this.userImpl.userAddMusicToPlaylistById(currentUser.getId(), playlistId, musicId);
        return "Music added to playlist successfully";
    }



}
