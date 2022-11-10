package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
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
public class TrackRepositoryTest {
    @Autowired
    private TrackRepository repo;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private LabelRepository labelRepository;
    private Album album;

    @Before
    public void setUp() {
        repo.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
        Artist artist = new Artist("john","@john","@john");
        artist = artistRepository.save(artist);
        Label label = new Label("jimmy","www.jimmy.com");
        label = labelRepository.save(label);
        album = new Album("artistTest", artist.getId(), LocalDate.parse("2022-11-09"), label.getId(), new BigDecimal("5.99"));
        album = albumRepository.save(album);
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        Track track = new Track(album.getId(),"trackTest",60);
        track = repo.save(track);

        Track actual = repo.findById(track.getId()).get();
        assertEquals(track,actual);

        repo.deleteById(track.getId());

        Optional<Track> emptyOptional = repo.findById(track.getId());
        assertFalse(emptyOptional.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        Track track = new Track(album.getId(), "trackTest",60);
        track = repo.save(track);

        Track changes = new Track(album.getId(), "changeTest",45);
        changes.setId(track.getId());
        repo.save(changes);

        track = repo.findById(track.getId()).get();

        assertEquals(changes,track);
    }

}