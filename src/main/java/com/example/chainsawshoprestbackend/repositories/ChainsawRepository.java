package com.example.chainsawshoprestbackend.repositories;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChainsawRepository extends JpaRepository<Chainsaw, Long> {
}
