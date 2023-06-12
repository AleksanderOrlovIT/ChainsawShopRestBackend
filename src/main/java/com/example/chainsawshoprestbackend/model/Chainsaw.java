package com.example.chainsawshoprestbackend.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chainsaws")
public class Chainsaw extends BaseEntity{

    @Builder
    public Chainsaw(Long id, String modelName, Integer price, Byte[] image, Integer quantity, Brand brand,
                    Set<Order> orders) {
        super(id);
        this.ModelName = modelName;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.brand = brand;
        if(orders != null)
            this.orders = orders;
    }

    private String ModelName;

    private Integer price;

    private Byte[] image;

    private Integer quantity;

    @ManyToOne
    private Brand brand;

    @ManyToMany(mappedBy = "chainsaws")
    private Set<Order> orders = new HashSet<>();
}


