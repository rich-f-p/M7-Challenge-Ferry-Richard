package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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
public class ArtistRecommendationRepositoryTest {

    @Autowired
    private ArtistRecommendationRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation() {
        ArtistRecommendation artist = new ArtistRecommendation(1, 1, true);
        artist = repo.save(artist);

        ArtistRecommendation actual = repo.findById(artist.getId()).get();
        assertEquals(artist, actual);

        repo.deleteById(artist.getId());

        Optional<ArtistRecommendation> emptyOptional = repo.findById(artist.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldGetAllAlbumRecommendations() {
        ArtistRecommendation artist1 = new ArtistRecommendation(1, 1, true);
        repo.save(artist1);
        ArtistRecommendation artist2 = new ArtistRecommendation(1, 2, true);
        repo.save(artist2);
        List<ArtistRecommendation> albumRecommendationList = repo.findAll();
        assertEquals(2, albumRecommendationList.size());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        ArtistRecommendation artist = new ArtistRecommendation(1, 1, true);
        artist = repo.save(artist);

        ArtistRecommendation changes = new ArtistRecommendation(1, 1, false);
        changes.setId(artist.getId());
        repo.save(changes);

        artist = repo.findById(artist.getId()).get();

        assertEquals(changes, artist);
    }
}