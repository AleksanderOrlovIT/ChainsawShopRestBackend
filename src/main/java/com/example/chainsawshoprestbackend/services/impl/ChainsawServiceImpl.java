package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.repositories.BrandRepository;
import com.example.chainsawshoprestbackend.repositories.ChainsawRepository;
import com.example.chainsawshoprestbackend.services.ChainsawService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChainsawServiceImpl implements ChainsawService {


    private final BrandRepository brandRepository;
    private final ChainsawRepository chainsawRepository;

    public ChainsawServiceImpl(BrandRepository brandRepository, ChainsawRepository chainsawRepository) {
        this.brandRepository = brandRepository;
        this.chainsawRepository = chainsawRepository;
    }

    @Override
    public List<Chainsaw> findAll() {
        return chainsawRepository.findAll();
    }

    @Override
    public Chainsaw findById(Long id) {
        return chainsawRepository.findById(id).orElse(null);
    }

    @Override
    public Chainsaw save(Chainsaw chainsaw) {
        if(chainsaw.getBrand() != null)
            brandRepository.save(chainsaw.getBrand());
        return chainsawRepository.save(chainsaw);
    }

    @Override
    public void delete(Chainsaw chainsaw) {
        chainsawRepository.delete(chainsaw);
    }

    @Override
    public void deleteById(Long id) {
        chainsawRepository.deleteById(id);
    }
}
