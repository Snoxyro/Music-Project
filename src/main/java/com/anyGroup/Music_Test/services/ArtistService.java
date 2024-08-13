package com.anyGroup.Music_Test.services;

import com.anyGroup.Music_Test.dto.ArtistResponse2;
import com.anyGroup.Music_Test.entities.ArtistEntity;
import com.anyGroup.Music_Test.repositories.ArtistRepository;
import com.anyGroup.Music_Test.serviceImpl.ArtistImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService implements ArtistImpl {

    private final ArtistRepository artistRepository;

    //Constructor
    @Autowired
    public ArtistService(ArtistRepository artistRepository) { this.artistRepository = artistRepository; }
    //Constructor

    @Override
    public List<ArtistResponse2> artistGetAll() {
        List<ArtistEntity> artistList = this.artistRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(artistList, new TypeToken<List<ArtistResponse2>>() {}.getType());
    }

    @Override
    public ArtistResponse2 artistGetById(Long artistId) {
        ArtistEntity artistEntity = this.artistRepository.findById(artistId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(artistEntity, ArtistResponse2.class);
    }
}
