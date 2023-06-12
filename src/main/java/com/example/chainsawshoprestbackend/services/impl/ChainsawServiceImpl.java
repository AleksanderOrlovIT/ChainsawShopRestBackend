package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.repositories.ChainsawRepository;
import com.example.chainsawshoprestbackend.services.ChainsawService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChainsawServiceImpl implements ChainsawService {

    private final ChainsawRepository chainsawRepository;

    public ChainsawServiceImpl(ChainsawRepository chainsawRepository) {
        this.chainsawRepository = chainsawRepository;
    }

    @Override
    public Set<Chainsaw> findAll() {
        return new HashSet<>(chainsawRepository.findAll());
    }

    @Override
    public Chainsaw findById(Long id) {
        return chainsawRepository.findById(id).orElse(null);
    }

    @Override
    public Chainsaw save(Chainsaw chainsaw) {
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
