package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.repositories.BrandRepository;
import com.example.chainsawshoprestbackend.services.BrandService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Set<Brand> findAll() {
        return new HashSet<>(brandRepository.findAll());
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        brandRepository.delete(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }
}
