package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labels")
public class LabelController {
    @Autowired
    LabelRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable Integer id){
        Optional<Label> label = repo.findById(id);
        if (label.isPresent())
            return label.get();
        else throw new IllegalArgumentException("findFailed: no label with that id");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody @Valid Label label){
        return repo.save(label);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody @Valid Label label){
        if(repo.findById(label.getId()).isPresent())
            repo.save(label);
        else throw new IllegalArgumentException("updateFailed: no label with thid id");
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        repo.deleteById(id);
    }
}
