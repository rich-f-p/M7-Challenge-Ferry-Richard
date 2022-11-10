package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackrecommendations")
public class TrackRecommendationController {

    @Autowired
    TrackRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getById(@PathVariable Integer id){
        Optional<TrackRecommendation> track = repo.findById(id);
        if(track.isPresent()){
            return track.get();
        }else throw new IllegalArgumentException("findFailed: no match for id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createRecommendation(@RequestBody TrackRecommendation track){
        return repo.save(track);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecommendation(@RequestBody TrackRecommendation track,@PathVariable Integer id){
        if (track.getId()>0 && track.getId().equals(id))
            repo.save(track);
        else throw new IllegalArgumentException("updateFailed: invalid id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
