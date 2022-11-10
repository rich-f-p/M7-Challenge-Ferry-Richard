package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LabelRecommendationRepositoryTest {
    @Autowired
    private LabelRecommendationRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        LabelRecommendation label = new LabelRecommendation(1,1,true);
        label = repo.save(label);

        LabelRecommendation actual = repo.findById(label.getId()).get();
        assertEquals(label,actual);

        repo.deleteById(label.getId());

        Optional<LabelRecommendation> emptyOptional = repo.findById(label.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldGetAllAlbumRecommendations(){
        LabelRecommendation label1 = new LabelRecommendation(1,1,true);
        repo.save(label1);
        LabelRecommendation label2 = new LabelRecommendation(1,2,true);
        repo.save(label2);
        List<LabelRecommendation> albumRecommendationList = repo.findAll();
        assertEquals(2,albumRecommendationList.size());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        LabelRecommendation label = new LabelRecommendation(1,1,true);
        label = repo.save(label);

        LabelRecommendation changes = new LabelRecommendation(1,1,false);
        changes.setId(label.getId());
        repo.save(changes);

        label = repo.findById(label.getId()).get();

        assertEquals(changes,label);
    }

}