package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtist(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable Integer id){
        Optional<Artist> artist = repo.findById(id);
        if (artist.isPresent())
            return artist.get();
        else throw new IllegalArgumentException("findFailed: no artist with that id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody @Valid Artist artist){
        return repo.save(artist);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody @Valid Artist artist){
        if(repo.findById(artist.getId()).isPresent())
            repo.save(artist);
        else throw new IllegalArgumentException("updateFailed: no artist with this id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
