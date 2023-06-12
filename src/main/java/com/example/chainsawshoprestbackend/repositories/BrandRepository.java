package com.example.chainsawshoprestbackend.repositories;

import com.example.chainsawshoprestbackend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
