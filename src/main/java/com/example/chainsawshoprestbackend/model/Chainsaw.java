package com.example.chainsawshoprestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chainsaw extends BaseEntity{

    @Builder
    public Chainsaw(Long id, String modelName, Integer price, Byte[] image, Brand brand) {
        super(id);
        this.ModelName = modelName;
        this.price = price;
        this.image = image;
        this.brand = brand;
    }

    private String ModelName;

    private Integer price;

    private Byte[] image;

    @ManyToOne
    private Brand brand;
}


