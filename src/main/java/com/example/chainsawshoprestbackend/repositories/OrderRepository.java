package com.example.chainsawshoprestbackend.repositories;

import com.example.chainsawshoprestbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
