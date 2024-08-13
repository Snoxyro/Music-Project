package com.anyGroup.Music_Test.dto;

import com.anyGroup.Music_Test.entities.MusicEntity;

import java.util.List;

public class PlaylistRequest {

    private String playlistName;

    private List<MusicEntity> musics;

    //Getters & Setters
    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }

    public List<MusicEntity> getMusics() { return musics; }
    public void setMusics(List<MusicEntity> musics) { this.musics = musics; }
    //Getters & Setters
}
