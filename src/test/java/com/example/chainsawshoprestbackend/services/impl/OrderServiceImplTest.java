package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.model.Order;
import com.example.chainsawshoprestbackend.repositories.ChainsawRepository;
import com.example.chainsawshoprestbackend.repositories.OrderRepository;
import jakarta.servlet.annotation.MultipartConfig;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ChainsawRepository chainsawRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void findAll() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(Order.builder().build(), Order.builder().build()));

        List<Order> list = orderService.findAll();

        assertEquals(2, list.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Order.builder().id(1L).build()));

        Order order = orderService.findById(1L);

        assertEquals(1L, order.getId());
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveWithRightChainsawsAndOrder() {
        when(orderRepository.save(any()))
                .thenReturn(Order.builder().id(1L)
                        .chainsaws(Arrays.asList(
                                Chainsaw.builder().quantity(100).build(),
                                Chainsaw.builder().quantity(50).build()))
                        .build());

        when(chainsawRepository.findById(1L)).thenReturn(Optional.ofNullable(Chainsaw.builder().quantity(40).build()));
        when(chainsawRepository.findById(2L)).thenReturn(Optional.ofNullable(Chainsaw.builder().quantity(40).build()));
        when(chainsawRepository.save(any())).thenReturn(null);

        Order order = Order.builder().chainsawQuantities(Map.of(1L, 40, 2L, 40)).build();
        Order savedOrder = orderService.save(order);

        assertEquals(2, savedOrder.getChainsaws().size());
        verify(orderRepository, times(1)).save(any());
        verify(chainsawRepository, times(2)).findById(anyLong());
        verify(chainsawRepository, times(2)).save(any());
    }

    @Test
    void deleteWithoutChainsaws() {
        orderService.delete(Order.builder().build());

        verify(orderRepository, times(1)).delete(any());
    }

    @Test
    void deleteByIdWithoutChainsaws() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(Order.builder().build()));
        orderService.deleteById(1L);

        verify(orderRepository, times(1)).deleteById(anyLong());
    }
}