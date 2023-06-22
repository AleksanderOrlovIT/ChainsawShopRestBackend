package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.services.BrandService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

    @Mock
    BrandService brandService;

    @InjectMocks
    BrandController brandController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }

    @Test
    void retrieveBrands() throws Exception{
        when(brandService.findAll())
                .thenReturn(Arrays.asList(Brand.builder().id(1L).build(), Brand.builder().id(1L).build()));

        mockMvc.perform(get("/brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveBrandById() throws Exception {
        // Prepare test data
        Long brandId = 1L;
        Brand expectedBrand = Brand.builder().id(brandId).url("url").build();
        when(brandService.findById(brandId)).thenReturn(expectedBrand);

        // Perform the request
        MvcResult result = mockMvc.perform(get("/brand/1", brandId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Brand> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<EntityModel<Brand>>() {});

        // Assertions
        assertEquals(expectedBrand.getUrl(), responseModel.getContent().getUrl());
        assertNotNull(responseModel.getLink("brand-chainsaws"));
    }

    @Test
    void retrieveBrandChainsaws() throws Exception{
        when(brandService.retrieveChainsawsByBrandId(anyLong()))
                .thenReturn(Arrays.asList(Chainsaw.builder().id(1L).build(), Chainsaw.builder().id(2L).build()));

        mockMvc.perform(get("/brand/1/chainsaws")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void deleteBrand() throws Exception {
        mockMvc.perform(delete("/brand/1"))
                .andExpect(status().is(204));
    }

    @Test
    void updateBrand() throws Exception {
        // Prepare test data
        Long brandId = 1L;
        Brand originalBrand = Brand.builder().id(brandId).url("url").build();
        Brand updatedBrand = Brand.builder().id(brandId).url("updated-url").build();
        when(brandService.findById(anyLong())).thenReturn(originalBrand);
        when(brandService.save(any())).thenReturn(updatedBrand);

        // Perform the request
        MvcResult result = mockMvc.perform(put("/brand/{id}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedBrand)))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        Brand responseBrand = new ObjectMapper().readValue(responseBody, Brand.class);

        // Assertions
        assertNotNull(responseBrand);
        assertEquals(updatedBrand.getUrl(), responseBrand.getUrl());
    }

    @Test
    void createBrand() throws Exception{
        // Prepare test data
        Brand brand = Brand.builder().id(null).url("url").build();
        Brand savedBrand = Brand.builder().id(1L).url("url").build();
        when(brandService.save(any())).thenReturn(savedBrand);

        // Perform the request
        MvcResult result = mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brand)))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify the response
        String locationHeader = result.getResponse().getHeader("Location");

        // Assertions
        assertNotNull(locationHeader);
        assertEquals("http://localhost/brand/1", locationHeader);
    }
}