package com.example.chainsawshoprestbackend.repositories;

import com.example.chainsawshoprestbackend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
