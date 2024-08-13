package com.anyGroup.Music_Test.dto;

public class MusicResponse3 {

    private Long musicId;

    private String musicName;

    private ArtistResponse3 artist;

    //Getters & Setters
    public Long getMusicId() { return musicId; }
    public void setMusicId(Long musicId) { this.musicId = musicId; }

    public String getMusicName() { return musicName; }
    public void setMusicName(String musicName) { this.musicName = musicName; }

    public ArtistResponse3 getArtist() { return artist; }
    public void setArtist(ArtistResponse3 artist) { this.artist = artist; }
    //Getters & Setters
}
