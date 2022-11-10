package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumrecommendations")
public class AlbumRecommendationController {

    @Autowired
    AlbumRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getById(@PathVariable Integer id){
        Optional<AlbumRecommendation> album = repo.findById(id);
        if(album.isPresent()){
            return album.get();
        }else throw new IllegalArgumentException("findFailed: no match for id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createRecommendation(@RequestBody AlbumRecommendation album){
        return repo.save(album);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecommendation(@RequestBody AlbumRecommendation album,@PathVariable Integer id){
        if (album.getId()>0 && album.getId().equals(id)) {
            repo.save(album);
        }
        else throw new IllegalArgumentException("updateFailed: invalid id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
