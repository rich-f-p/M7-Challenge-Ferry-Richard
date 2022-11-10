package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelrecommendations")
public class LabelRecommendationController {

    @Autowired
    LabelRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getById(@PathVariable Integer id){
        Optional<LabelRecommendation> label = repo.findById(id);
        if(label.isPresent()){
            return label.get();
        }else throw new IllegalArgumentException("findFailed: no match for id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createRecommendation(@RequestBody LabelRecommendation label){
        return repo.save(label);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecommendation(@RequestBody LabelRecommendation label, @PathVariable Integer id){
        if (label.getId()>0 && label.getId().equals(id))
            repo.save(label);
        else throw new IllegalArgumentException("updateFailed: invalid id");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
