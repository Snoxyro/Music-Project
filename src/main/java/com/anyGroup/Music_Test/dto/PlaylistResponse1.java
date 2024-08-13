package com.anyGroup.Music_Test.dto;

import java.util.List;

public class PlaylistResponse1 {

    //private Long playlistId;

    private String playlistName;

    private List<MusicResponse1> musics;

    //Getters & Setters
    //public Long getPlaylistId() { return playlistId; }
    //public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }

    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }

    public List<MusicResponse1> getMusics() { return musics; }
    public void setMusics(List<MusicResponse1> musics) { this.musics = musics; }
    //Getters & Setters
}
