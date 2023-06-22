package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Order;
import com.example.chainsawshoprestbackend.services.OrderService;
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
import java.util.Map;

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
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void retrieveOrders() throws Exception{
        when(orderService.findAll())
                .thenReturn(Arrays.asList(Order.builder().id(1L).build(), Order.builder().id(1L).build()));

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveOrderById() throws Exception {
        Long orderID = 1L;
        Order expectedOrder = Order.builder().id(orderID).build();
        when(orderService.findById(orderID)).thenReturn(expectedOrder);

        MvcResult result = mockMvc.perform(get("/order/1", orderID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Order responseCustomer = new ObjectMapper().readValue(responseBody, Order.class);

        assertNotNull(responseCustomer);
        assertEquals(expectedOrder.getId(), responseCustomer.getId());
    }

    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(delete("/order/1"))
                .andExpect(status().is(204));
    }

    @Test
    void updateOrder() throws Exception {
        Long orderId = 1L;
        Order originalOrder = Order.builder().id(orderId).chainsawQuantities(Map.of(1L, 1)).build();
        Order updatedOrder = Order.builder().id(orderId).chainsawQuantities(Map.of(1L, 1, 2L, 2)).build();
        when(orderService.findById(anyLong())).thenReturn(originalOrder);
        when(orderService.save(any())).thenReturn(updatedOrder);

        MvcResult result = mockMvc.perform(put("/order/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Order responseOrder = new ObjectMapper().readValue(responseBody, Order.class);

        assertNotNull(responseOrder);
        assertEquals(updatedOrder.getChainsawQuantities().size(), responseOrder.getChainsawQuantities().size());
    }

    @Test
    void createOrder() throws Exception{
        Order order = Order.builder().id(null).build();
        Order savedOrder = Order.builder().id(1L).chainsawQuantities(Map.of(1L, 1)).build();
        when(orderService.save(any())).thenReturn(savedOrder);

        MvcResult result = mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/order/1", locationHeader);
    }
}