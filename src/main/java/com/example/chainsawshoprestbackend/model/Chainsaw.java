package com.example.chainsawshoprestbackend.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chainsaw extends BaseEntity{

    private String Model;

    private Integer price;

    private Byte[] image;
}


