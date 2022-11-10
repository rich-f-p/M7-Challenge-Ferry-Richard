package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AlbumRecommendationRepository repo;
    private String inputJson;
    private String outputJson;

    private AlbumRecommendation inputAlbum;
    private AlbumRecommendation outputAlbum;
    private List<AlbumRecommendation> recommendationList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        inputAlbum = new AlbumRecommendation(1,1,true);
        outputAlbum = new AlbumRecommendation(1,1,true);
        AlbumRecommendation album1 = new AlbumRecommendation(1,1,true);
        AlbumRecommendation album2 = new AlbumRecommendation(1,2,false);
        album1.setId(1);
        album2.setId(2);
        outputAlbum.setId(1);

        recommendationList.add(album1);
        recommendationList.add(album2);

        inputJson = mapper.writeValueAsString(inputAlbum);
        outputJson = mapper.writeValueAsString(outputAlbum);
    }

    @Test
    public void shouldReturnAllInCollection() throws Exception {
        String listJson = mapper.writeValueAsString(recommendationList);
        doReturn(recommendationList).when(repo).findAll();
        mockMvc.perform(get("/albumrecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(listJson));
    }

    @Test
    public void shouldReturnById() throws Exception {
        doReturn(Optional.of(outputAlbum)).when(repo).findById(1);
        mockMvc.perform(get("/albumrecommendations/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldCreateNewRecommendation() throws Exception {
        doReturn(outputAlbum).when(repo).save(inputAlbum);
        mockMvc.perform(post("/albumrecommendations")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateRecommendation() throws Exception {
        mockMvc.perform(put("/albumrecommendations/{id}",1)
                        .content(outputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteRecommendation() throws Exception {
        mockMvc.perform(delete("/albumrecommendations/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}