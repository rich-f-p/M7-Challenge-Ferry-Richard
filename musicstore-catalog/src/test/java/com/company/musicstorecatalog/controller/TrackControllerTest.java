package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private TrackRepository repo;
    private String inputJson;
    private String outputJson;

    private Track inputAlbum;
    private Track outputAlbum;
    private List<Track> recommendationList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        inputAlbum = new Track(1,"testTitle",60);
        outputAlbum = new Track(1,"testTitle",60);
        Track title1 = new Track(1,"title1",60);
        Track title2 = new Track(1,"title2",60);
        title1.setId(1);
        title2.setId(2);
        outputAlbum.setId(1);

        recommendationList.add(title1);
        recommendationList.add(title2);

        inputJson = mapper.writeValueAsString(inputAlbum);
        outputJson = mapper.writeValueAsString(outputAlbum);
    }

    @Test
    public void shouldReturnAllInCollection() throws Exception {
        String listJson = mapper.writeValueAsString(recommendationList);
        doReturn(recommendationList).when(repo).findAll();
        mockMvc.perform(get("/tracks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(listJson));
    }

    @Test
    public void shouldReturnById() throws Exception {
        doReturn(Optional.of(outputAlbum)).when(repo).findById(1);
        mockMvc.perform(get("/tracks/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldCreateNewRecommendation() throws Exception {
        doReturn(outputAlbum).when(repo).save(inputAlbum);
        mockMvc.perform(post("/tracks")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateRecommendation() throws Exception {
        mockMvc.perform(put("/tracks/{id}",1)
                        .content(outputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteRecommendation() throws Exception {
        mockMvc.perform(delete("/tracks/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}