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
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public List<MusicEntity> getMusics() { return musics; }
    public void setMusics(List<MusicEntity> musics) { this.musics = musics; }
    //Getters & Setters
}
