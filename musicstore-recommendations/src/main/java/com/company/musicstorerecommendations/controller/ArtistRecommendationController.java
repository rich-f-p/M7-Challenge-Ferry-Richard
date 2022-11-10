package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistrecommendations")
public class ArtistRecommendationController {

    @Autowired
    ArtistRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getById(@PathVariable Integer id){
        Optional<ArtistRecommendation> artist = repo.findById(id);
        if(artist.isPresent()){
            return artist.get();
        }else throw new IllegalArgumentException("findFailed: no match for id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createRecommendation(@RequestBody ArtistRecommendation artist){
        return repo.save(artist);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecommendation(@RequestBody ArtistRecommendation artist,@PathVariable Integer id){
        if (artist.getId()>0 && artist.getId().equals(id))
            repo.save(artist);
        else throw new IllegalArgumentException("updateFailed: invalid id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        repo.deleteById(id);
    }

}
