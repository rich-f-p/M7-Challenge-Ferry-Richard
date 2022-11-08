package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    TrackRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable Integer id){
        Optional<Track> track = repo.findById(id);
        if(track.isPresent())
            return track.get();
        else throw new IllegalArgumentException("findFailed: no track with that id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@RequestBody @Valid Track track){
        return repo.save(track);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid Track track){
        if(repo.findById(track.getId()).isPresent())
            repo.save(track);
        else throw new IllegalArgumentException("updateFailed: no track with this id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
