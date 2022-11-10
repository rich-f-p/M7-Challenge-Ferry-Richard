package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {

    @Autowired
    private AlbumRecommendationRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        AlbumRecommendation album = new AlbumRecommendation(1,1,true);
        album = repo.save(album);

        AlbumRecommendation actual = repo.findById(album.getId()).get();
        assertEquals(album,actual);

        repo.deleteById(album.getId());

        Optional<AlbumRecommendation> emptyOptional = repo.findById(album.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldGetAllAlbumRecommendations(){
        AlbumRecommendation album1 = new AlbumRecommendation(1,1,true);
        repo.save(album1);
        AlbumRecommendation album2 = new AlbumRecommendation(1,2,true);
        repo.save(album2);
        List<AlbumRecommendation> albumRecommendationList = repo.findAll();
        assertEquals(2,albumRecommendationList.size());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        AlbumRecommendation album = new AlbumRecommendation(1,1,true);
        album = repo.save(album);

        AlbumRecommendation changes = new AlbumRecommendation(1,1,false);
        changes.setId(album.getId());
        repo.save(changes);

        album = repo.findById(album.getId()).get();

        assertEquals(changes,album);
    }

}