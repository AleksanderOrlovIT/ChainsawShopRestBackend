package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Order;
import com.example.chainsawshoprestbackend.services.CustomerService;
import com.example.chainsawshoprestbackend.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> retrieveOrders(){
        return orderService.findAll();
    }

    @GetMapping("/order/{id}")
    public Order retrieveOrderById(@PathVariable Long id){
        return orderService.findById(id);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/order/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order){
        if(!order.getId().equals(id) || orderService.findById(id) == null)
            return null;
        return orderService.save(order);
    }

    @PostMapping("/order")
    public Order createOrder(@RequestBody Order order){
        order.setId(null);
        return orderService.save(order);
    }
}
