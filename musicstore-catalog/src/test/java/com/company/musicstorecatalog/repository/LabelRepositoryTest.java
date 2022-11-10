package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
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
public class LabelRepositoryTest {
    @Autowired
    private LabelRepository repo;

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Before
    public void setUp() {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        Label label = new Label("jimmy","www.jimmy.com");
        label = repo.save(label);

        Label actual = repo.findById(label.getId()).get();
        assertEquals(label,actual);

        repo.deleteById(label.getId());

        Optional<Label> emptyOptional = repo.findById(label.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        Label label = new Label("jimmy","www.jimmy.com");
        label = repo.save(label);

        Label changes = new Label("jimmy","www.jimmy.com");
        changes.setId(label.getId());
        repo.save(changes);

        label = repo.findById(label.getId()).get();

        assertEquals(changes,label);
    }

}