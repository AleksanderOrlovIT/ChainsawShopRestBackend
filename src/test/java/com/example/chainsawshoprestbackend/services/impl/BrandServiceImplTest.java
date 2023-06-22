package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.repositories.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    BrandRepository brandRepository;

    @InjectMocks
    BrandServiceImpl brandService;

    @Test
    void findAll() {
        when(brandRepository.findAll()).thenReturn(Arrays.asList(Brand.builder().build(), Brand.builder().build()));

        List<Brand> list = brandService.findAll();

        assertEquals(2, list.size());
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(brandRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Brand.builder().id(1L).url("url").build()));

        Brand brand = brandService.findById(1L);

        assertEquals("url", brand.getUrl());
        verify(brandRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        when(brandRepository.save(any()))
                .thenReturn(Brand.builder().id(1L).url("url").build());

        Brand brand = Brand.builder().build();
        Brand savedBrand = brandService.save(brand);

        assertEquals("url", savedBrand.getUrl());
        verify(brandRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        brandService.delete(Brand.builder().build());

        verify(brandRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        brandService.deleteById(1L);

        verify(brandRepository, times(1)).deleteById(anyLong());
    }
}