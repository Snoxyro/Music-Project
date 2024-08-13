package com.anyGroup.Music_Test.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "musics")
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "music_name")
    private String musicName;
    @ManyToOne
    private ArtistEntity artist;

    //Constructor
    public MusicEntity() {}
    public MusicEntity(String musicName, ArtistEntity artist) {
        this.musicName = musicName;
        this.artist = artist;
    }
    //Constructor

    //Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getMusicName() { return musicName; }
    public void setMusicName(String musicName) { this.musicName = musicName; }

    public ArtistEntity getArtist() { return artist; }
    public void setArtist(ArtistEntity artist) { this.artist = artist; }
    //Getters & Setters
}
