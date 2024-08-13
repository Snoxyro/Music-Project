package com.anyGroup.Music_Test.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "playlists")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "playlist_name")
    private String playlistName;
    @ManyToMany
    @JoinTable(name = "playlists-musics",
            joinColumns = {@JoinColumn(name = "playlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "music_id")})
    private List<MusicEntity> musics;

    //Constructor
    public PlaylistEntity() {}
    public PlaylistEntity(String playlistName, List<MusicEntity> musics) {
        this.playlistName = playlistName;
        this.musics = musics;
    }
    //Constructor

    //Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }

    public List<MusicEntity> getMusics() { return musics; }
    public void setMusics(List<MusicEntity> musics) { this.musics = musics; }
    //Getters & Setters
}
