package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.repositories.BrandRepository;
import com.example.chainsawshoprestbackend.repositories.ChainsawRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChainsawServiceImplTest {

    @Mock
    ChainsawRepository chainsawRepository;

    @Mock
    BrandRepository brandRepository;

    @InjectMocks
    ChainsawServiceImpl chainsawService;

    @Test
    void findAll() {
        when(chainsawRepository.findAll()).thenReturn(Arrays.asList(Chainsaw.builder().build(), Chainsaw.builder().build()));

        List<Chainsaw> list = chainsawService.findAll();

        assertEquals(2, list.size());
        verify(chainsawRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(chainsawRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Chainsaw.builder().id(1L).price(100).build()));

        Chainsaw chainsaw = chainsawService.findById(1L);

        assertEquals(100, chainsaw.getPrice());
        verify(chainsawRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        when(chainsawRepository.save(any()))
                .thenReturn(Chainsaw.builder().id(1L).price(100).build());

        Chainsaw chainsaw = Chainsaw.builder().build();
        Chainsaw savedChainsaw = chainsawService.save(chainsaw);

        assertEquals(savedChainsaw.getPrice(), 100);
        verify(chainsawRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        chainsawService.delete(Chainsaw.builder().build());

        verify(chainsawRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        chainsawService.deleteById(1L);

        verify(chainsawRepository, times(1)).deleteById(anyLong());
    }
}