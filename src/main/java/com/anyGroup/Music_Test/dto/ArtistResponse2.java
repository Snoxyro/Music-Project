package com.anyGroup.Music_Test.dto;

import java.util.List;

public class ArtistResponse2 {

    private Long artistId;

    private String firstName;

    private String lastName;

    private List<MusicResponse2> musics;

    //Getters & Setters
    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public List<MusicResponse2> getMusics() { return musics; }
    public void setMusics(List<MusicResponse2> musics) { this.musics = musics; }
    //Getters & Setters
}
