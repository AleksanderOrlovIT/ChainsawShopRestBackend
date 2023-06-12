package com.example.chainsawshoprestbackend.bootstrap;

import com.example.chainsawshoprestbackend.model.Brand;
import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.services.BrandService;
import com.example.chainsawshoprestbackend.services.ChainsawService;
import com.example.chainsawshoprestbackend.services.impl.BrandServiceImpl;
import com.example.chainsawshoprestbackend.services.impl.ChainsawServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialBootstrap implements CommandLineRunner {

    private final BrandService brandService;
    private final ChainsawService chainsawService;

    public InitialBootstrap(BrandService brandService, ChainsawService chainsawService) {
        this.brandService = brandService;
        this.chainsawService = chainsawService;
    }

    @Override
    public void run(String... args) throws Exception {
        Brand brand1 = Brand.builder().name("First Brand").email("FirstEmail@gmail.com").build();
        Brand brand2 = Brand.builder().name("Second Brand").email("SecondEmail@gmail.com").build();

        Chainsaw woodenChainsaw = Chainsaw.builder().modelName("Wooden").price(100).quantity(100).brand(brand1).build();
        Chainsaw woodenAndMetalChainsaw = Chainsaw.builder().modelName("WoodenAndMetal")
                .price(200).quantity(50).brand(brand1).build();
        Chainsaw metalChainsaw = Chainsaw.builder().modelName("Metal").price(500).quantity(5).brand(brand2).build();

        brand1.getChainsaws().add(woodenChainsaw);
        brand1.getChainsaws().add(woodenAndMetalChainsaw);
        brand2.getChainsaws().add(metalChainsaw);

        brandService.save(brand1);
        brandService.save(brand2);

        chainsawService.save(woodenChainsaw);
        chainsawService.save(woodenAndMetalChainsaw);
        chainsawService.save(metalChainsaw);
    }
}
