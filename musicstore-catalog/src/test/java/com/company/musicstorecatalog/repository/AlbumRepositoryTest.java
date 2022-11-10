package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
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
public class AlbumRepositoryTest {
    @Autowired
    private AlbumRepository repo;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TrackRepository trackRepository;

    private Artist artist;
    private Label label;

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        repo.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();

        artist = new Artist("john","@john","@john");
        artist = artistRepository.save(artist);
        label = new Label("jimmy","www.jimmy.com");
        label = labelRepository.save(label);
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        Album album = new Album("artistTest", artist.getId(), LocalDate.parse("2022-11-09"), label.getId(), new BigDecimal("5.99"));
        album = repo.save(album);

        Album actual = repo.findById(album.getId()).get();
        assertEquals(album,actual);

        repo.deleteById(album.getId());

        Optional<Album> emptyOptional = repo.findById(album.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        Album album = new Album("artistTest", artist.getId(), LocalDate.parse("2022-11-09"), label.getId(), new BigDecimal("5.99"));
        album = repo.save(album);

        Album changes = new Album("artistChanges", artist.getId(), LocalDate.parse("2022-11-10"),label.getId(),new BigDecimal("6.99"));
        changes.setId(album.getId());
        repo.save(changes);

        album = repo.findById(album.getId()).get();

        assertEquals(changes,album);
    }

}