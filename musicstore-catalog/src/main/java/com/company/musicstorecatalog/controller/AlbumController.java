package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable Integer id){
        Optional<Album> album = repo.findById(id);
        if (album.isPresent())
            return album.get();
        else throw new IllegalArgumentException("findFailed: no album with that id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody @Valid Album album){
        return repo.save(album);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody @Valid Album album){
        if(repo.findById(album.getId()).isPresent())
            repo.save(album);
        else throw new IllegalArgumentException("updateFailed: no album with this id");
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id ){
        repo.deleteById(id);
    }
}
