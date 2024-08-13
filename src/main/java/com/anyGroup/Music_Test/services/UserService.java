package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.PlaylistRequest;
import com.anyGroup.Music_Test.dto.PlaylistResponse1;
import com.anyGroup.Music_Test.dto.UserRegisterRequest;
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
    public UserEntity createAdministrator(UserRegisterRequest input) {
        Optional<RoleEntity> optionalRole = this.roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) { return null; }

        UserEntity user = new UserEntity()
                .setUsername(input.getUsername())
                .setEmail(input.getEmail())
                .setPassword(this.passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());

        return this.userRepository.save(user);
    }

    //===============================================================================
    //===============================================================================

    @Override
    public UserResponse1 userGetById(Long userId) throws ResponseStatusException {
        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserResponse1.class);
    }

    @Override
    public void userCreatePlaylist(Long userId, PlaylistRequest playlist) throws ResponseStatusException {
        if(playlist.getPlaylistName() == null) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist name can't be null"); }
        if(playlist.getPlaylistName().isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist name can't be empty"); }
        if(playlist.getPlaylistName().length() > 20) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist name can't contain more than 20 characters"); }

        PlaylistEntity newPlaylist = new PlaylistEntity().setPlaylistName(playlist.getPlaylistName());
        this.playlistRepository.save(newPlaylist);

        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<PlaylistEntity> playlists = user.getPlaylists();
        playlists.add(newPlaylist);
        user.setPlaylists(playlists);

        this.userRepository.save(user);
    }

    @Override
    public List<PlaylistResponse1> userGetAllPlaylists(Long userId) throws ResponseStatusException {
        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user.getPlaylists(), new TypeToken<List<PlaylistResponse1>>() {}.getType());
    }

    @Override
    public PlaylistResponse1 userGetPlaylistById(Long userId, Long playlistId) throws ResponseStatusException {
        PlaylistEntity playlist = this.playlistRepository.findById(playlistId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Check if playlist with given id belongs to the user
        boolean found = false;
        for (PlaylistEntity uPlaylist : user.getPlaylists()) { if (uPlaylist.getId() == playlistId) { found = true; break; } }
        if (!found) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Playlist doesn't belong to the user");}

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(playlist, PlaylistResponse1.class);
    }

    @Override
    public void userAddMusicToPlaylistById(Long userId, Long playlistId, Long musicId) throws ResponseStatusException  {
        PlaylistEntity playlist = this.playlistRepository.findById(playlistId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
        MusicEntity music = this.musicRepository.findById(musicId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Music not found"));
        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //Check if playlist with given id belongs to the user
        boolean found = false;
        for (PlaylistEntity uPlaylist : user.getPlaylists()) { if (uPlaylist.getId() == playlistId) { found = true; break; } }
        if (!found) {throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Playlist doesn't belong to the user");}

        List<MusicEntity> musics = playlist.getMusics();
        musics.add(music);
        playlist.setMusics(musics);

        this.playlistRepository.save(playlist);
    }
}
