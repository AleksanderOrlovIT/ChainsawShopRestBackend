package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Customer;
import com.example.chainsawshoprestbackend.services.CustomerService;
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
class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void retrieveCustomers() throws Exception{
        when(customerService.findAll())
                .thenReturn(Arrays.asList(Customer.builder().id(1L).build(), Customer.builder().id(1L).build()));

        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveCustomerById() throws Exception {
        Long customerId = 1L;
        Customer expectedCustomer = Customer.builder().id(customerId).email("email").build();
        when(customerService.findById(customerId)).thenReturn(expectedCustomer);

        MvcResult result = mockMvc.perform(get("/customer/1", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Customer responseCustomer = new ObjectMapper().readValue(responseBody, Customer.class);

        assertNotNull(responseCustomer);
        assertEquals(expectedCustomer.getEmail(), responseCustomer.getEmail());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().is(204));
    }

    @Test
    void updateCustomer() throws Exception {
        Long customerId = 1L;
        Customer originalCustomer = Customer.builder().id(customerId).email("email").build();
        Customer updatedCustomer = Customer.builder().id(customerId).email("newEmail").build();
        when(customerService.findById(anyLong())).thenReturn(originalCustomer);
        when(customerService.save(any())).thenReturn(updatedCustomer);

        MvcResult result = mockMvc.perform(put("/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Customer responseCustomer = new ObjectMapper().readValue(responseBody, Customer.class);

        assertNotNull(responseCustomer);
        assertEquals(updatedCustomer.getEmail(), responseCustomer.getEmail());
    }

    @Test
    void createCustomer() throws Exception{
        Customer customer = Customer.builder().id(null).email("email").build();
        Customer savedCustomer = Customer.builder().id(1L).email("newEmail").build();
        when(customerService.save(any())).thenReturn(savedCustomer);

        MvcResult result = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/customer/1", locationHeader);
    }
}