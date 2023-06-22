package com.example.chainsawshoprestbackend.controller;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;
import org.springframework.hateoas.EntityModel;
import com.example.chainsawshoprestbackend.services.BrandService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public List<Brand> retrieveBrands(){
        return brandService.findAll();
    }

    @GetMapping("/brand/{id}")
    public EntityModel<Brand> retrieveBrandById(@PathVariable Long id){
        Brand brand = brandService.findById(id);
        if(brand == null)
            return null;
        EntityModel<Brand> entityModel = EntityModel.of(brand);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveBrandChainsaws(id));
        entityModel.add(link.withRel("brand-chainsaws"));
        return entityModel;
    }

    @GetMapping("/brand/{id}/chainsaws")
    public List<Chainsaw> retrieveBrandChainsaws(@PathVariable Long id){
        return brandService.retrieveChainsawsByBrandId(id);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id){
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/brand/{id}")
    public Brand updateBrand(@PathVariable Long id, @RequestBody Brand brand){
        if(!brand.getId().equals(id) || brandService.findById(id) == null)
            return null;
        return brandService.save(brand);
    }

    @PostMapping("/brand")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand){
        brand.setId(null);
        Brand savedBrand = brandService.save(brand);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBrand.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
