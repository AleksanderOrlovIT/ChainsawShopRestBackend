package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Customer;
import com.example.chainsawshoprestbackend.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> retrieveCustomers(){
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    public Customer retrieveCustomerById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customer/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        if(!customer.getId().equals(id) || customerService.findById(id) == null)
            return null;
        return customerService.save(customer);
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        customer.setId(null);
        Customer savedCustomer = customerService.save(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
