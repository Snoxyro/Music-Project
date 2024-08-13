package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.PlaylistRequest;
import com.anyGroup.Music_Test.dto.PlaylistResponse1;
import com.anyGroup.Music_Test.dto.RegisterUserDto;
import com.anyGroup.Music_Test.dto.UserResponse1;
import com.anyGroup.Music_Test.entities.*;
import com.anyGroup.Music_Test.repositories.MusicRepository;
import com.anyGroup.Music_Test.repositories.PlaylistRepository;
import com.anyGroup.Music_Test.repositories.RoleRepository;
import com.anyGroup.Music_Test.repositories.UserRepository;

import com.anyGroup.Music_Test.serviceImpl.UserImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserImpl {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PlaylistRepository playlistRepository;

    private final MusicRepository musicRepository;

    private final PasswordEncoder passwordEncoder;

    //Constructor
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PlaylistRepository playlistRepository,
                       MusicRepository musicRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //Constructor

    @Override
    public List<UserResponse1> allUsers() {
        List<UserEntity> users = this.userRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(users, new TypeToken<List<UserResponse1>>() {}.getType());
    }

    //TODO Remove this and make a role changing system instead
    public UserEntity createAdministrator(RegisterUserDto input) {
        Optional<RoleEntity> optionalRole = this.roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) { return null; }

        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return this.userRepository.save(user);
    }

    //===============================================================================
    //===============================================================================

    @Override
    public UserResponse1 userGetById(Long userId) {
        UserEntity user = this.userRepository.findById(userId).get();

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserResponse1.class);
    }

    @Override
    public void userCreatePlaylist(Long userId, PlaylistRequest playlist) {
        PlaylistEntity newPlaylist = new PlaylistEntity();
        newPlaylist.setPlaylistName(playlist.getPlaylistName());
        this.playlistRepository.save(newPlaylist);

        UserEntity user = this.userRepository.findById(userId).get();
        List<PlaylistEntity> playlists = user.getPlaylists();
        playlists.add(newPlaylist);
        user.setPlaylists(playlists);

        this.userRepository.save(user);
    }

    @Override
    public List<PlaylistResponse1> userGetAllPlaylists(Long userId) {
        UserEntity user = this.userRepository.findById(userId).get();

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user.getPlaylists(), new TypeToken<List<PlaylistResponse1>>() {}.getType());
    }

    @Override
    public PlaylistResponse1 userGetPlaylistById(Long userId, Long playlistId) {
        PlaylistEntity playlist = this.playlistRepository.findById(playlistId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));

        //Check if playlist with given id belongs to the user
        List<PlaylistEntity> userPlaylists = this.userRepository.findById(userId).get().getPlaylists();
        boolean found = false;
        for (PlaylistEntity uPlaylist : userPlaylists) { if (uPlaylist.getId() == playlistId) { found = true; break; } }
        if (!found) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Playlist doesn't belong to the user");}

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(playlist, PlaylistResponse1.class);
    }

    @Override
    public void userAddMusicToPlaylistById(Long userId, Long playlistId, Long musicId) {
        PlaylistEntity playlist = this.playlistRepository.findById(playlistId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
        MusicEntity music = this.musicRepository.findById(musicId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Music not found"));

        //Check if playlist with given id belongs to the user
        List<PlaylistEntity> userPlaylists = this.userRepository.findById(userId).get().getPlaylists();
        boolean found = false;
        for (PlaylistEntity uPlaylist : userPlaylists) { if (uPlaylist.getId() == playlistId) { found = true; break; } }
        if (!found) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Playlist doesn't belong to the user");}

        List<MusicEntity> musics = playlist.getMusics();
        musics.add(music);
        playlist.setMusics(musics);

        this.playlistRepository.save(playlist);
    }
}
