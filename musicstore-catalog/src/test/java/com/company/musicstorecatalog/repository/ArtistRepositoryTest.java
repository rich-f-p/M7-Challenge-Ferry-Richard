package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {
    @Autowired
    private ArtistRepository repo;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        repo.deleteAll();
        labelRepository.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        Artist artist = new Artist("john","@john","@john");
        artist = repo.save(artist);

        Artist actual = repo.findById(artist.getId()).get();
        assertEquals(artist,actual);

        repo.deleteById(artist.getId());

        Optional<Artist> emptyOptional = repo.findById(artist.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        Artist artist = new Artist("john","@john","@john");
        artist = repo.save(artist);

        Artist changes = new Artist("john","@john","@john");
        changes.setId(artist.getId());
        repo.save(changes);

        artist = repo.findById(artist.getId()).get();

        assertEquals(changes,artist);
    }

}