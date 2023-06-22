package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.services.ChainsawService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ChainsawControllerTest {

    @Mock
    ChainsawService chainsawService;

    @InjectMocks
    ChainsawController chainsawController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chainsawController).build();
    }

    @Test
    void retrieveChainsaws() throws Exception{
        when(chainsawService.findAll())
                .thenReturn(Arrays.asList(Chainsaw.builder().id(1L).build(), Chainsaw.builder().id(1L).build()));

        mockMvc.perform(get("/chainsaws")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveChainsawById() throws Exception {
        Long chainsawId = 1L;
        Chainsaw expectedChainsaw = Chainsaw.builder().id(chainsawId).price(100).build();
        when(chainsawService.findById(chainsawId)).thenReturn(expectedChainsaw);

        MvcResult result = mockMvc.perform(get("/chainsaw/1", chainsawId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Chainsaw responseChainsaw = new ObjectMapper().readValue(responseBody, Chainsaw.class);

        assertNotNull(responseChainsaw);
        assertEquals(expectedChainsaw.getPrice(), responseChainsaw.getPrice());
    }

    @Test
    void deleteChainsaw() throws Exception {
        mockMvc.perform(delete("/chainsaw/1"))
                .andExpect(status().is(204));
    }

    @Test
    void updateChainsaw() throws Exception {
        Long chainsawId = 1L;
        Chainsaw originalChainsaw = Chainsaw.builder().id(chainsawId).price(100).build();
        Chainsaw updatedChainsaw = Chainsaw.builder().id(chainsawId).price(200).build();
        when(chainsawService.findById(anyLong())).thenReturn(originalChainsaw);
        when(chainsawService.save(any())).thenReturn(updatedChainsaw);

        MvcResult result = mockMvc.perform(put("/chainsaw/{id}", chainsawId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedChainsaw)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Chainsaw responseChainsaw = new ObjectMapper().readValue(responseBody, Chainsaw.class);

        assertNotNull(responseChainsaw);
        assertEquals(updatedChainsaw.getPrice(), responseChainsaw.getPrice());
    }

    @Test
    void createChainsaw() throws Exception{
        Chainsaw chainsaw = Chainsaw.builder().id(null).price(100).build();
        Chainsaw savedChainsaw = Chainsaw.builder().id(1L).price(200).build();
        when(chainsawService.save(any())).thenReturn(savedChainsaw);

        MvcResult result = mockMvc.perform(post("/chainsaw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(chainsaw)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/chainsaw/1", locationHeader);
    }
}