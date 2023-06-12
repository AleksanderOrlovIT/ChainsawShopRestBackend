package com.example.chainsawshoprestbackend.services;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;

import java.util.List;

public interface BrandService extends CrudService<Brand, Long>{
    List<Chainsaw> retrieveChainsawsByBrandId(Long id);
}
