package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.repositories.BrandRepository;
import com.example.chainsawshoprestbackend.services.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public List<Brand> retrieveBrands(){
        return brandService.findAll();
    }

    @GetMapping("/brand/{id}")
    public Brand retrieveBrandById(@PathVariable Long id){
        return brandService.findById(id);
    }

    @GetMapping("/brand/{id}/chainsaws")
    public List<Chainsaw> retrieveBrandChainsaws(@PathVariable Long id){
        return brandService.retrieveChainsawsByBrandId(id);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id){
        brandService.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/brand/{id}")
    public Brand updateBrand(@PathVariable Long id, @RequestBody Brand brand){
        brand.setId(id);
        return brandService.save(brand);
    }

    @PostMapping("/brand/{id}")
    public Brand createBrand(@PathVariable Long id, @RequestBody Brand brand){
        brand.setId(null);
        return brandService.save(brand);
    }
}
