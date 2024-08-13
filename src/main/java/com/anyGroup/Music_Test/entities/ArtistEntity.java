package com.anyGroup.Music_Test.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "artist")
    private List<MusicEntity> musics;

    //Constructor
    public ArtistEntity() {}
    public ArtistEntity(String firstName, String lastName, List<MusicEntity> musics) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.musics = musics;
    }
    //Constructor

    //Getters & Setters
    public long getId() { return id; }
    public ArtistEntity setId(long id) { this.id = id; return this; }

    public String getFirstName() { return firstName; }
    public ArtistEntity setFirstName(String firstName) { this.firstName = firstName; return this; }

    public String getLastName() { return lastName; }
    public ArtistEntity setLastName(String lastName) { this.lastName = lastName; return this; }

    public List<MusicEntity> getMusics() { return musics; }
    public ArtistEntity setMusics(List<MusicEntity> musics) { this.musics = musics; return this; }
    //Getters & Setters
}
