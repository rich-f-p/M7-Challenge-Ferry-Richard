package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRecommendationRepositoryTest {
    @Autowired
    private TrackRecommendationRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        TrackRecommendation track = new TrackRecommendation(1,1,true);
        track = repo.save(track);

        TrackRecommendation actual = repo.findById(track.getId()).get();
        assertEquals(track,actual);

        repo.deleteById(track.getId());

        Optional<TrackRecommendation> emptyOptional = repo.findById(track.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldGetAllAlbumRecommendations(){
        TrackRecommendation track1 = new TrackRecommendation(1,1,true);
        repo.save(track1);
        TrackRecommendation track2 = new TrackRecommendation(1,2,true);
        repo.save(track2);
        List<TrackRecommendation> albumRecommendationList = repo.findAll();
        assertEquals(2,albumRecommendationList.size());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        TrackRecommendation track = new TrackRecommendation(1,1,true);
        track = repo.save(track);

        TrackRecommendation changes = new TrackRecommendation(1,1,false);
        changes.setId(track.getId());
        repo.save(changes);

        track = repo.findById(track.getId()).get();

        assertEquals(changes,track);
    }

}