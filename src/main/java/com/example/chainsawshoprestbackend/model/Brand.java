package com.example.chainsawshoprestbackend.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Brand extends BaseEntity{

    @Builder
    public Brand(Long id, String name, String url, String email, Set<Chainsaw> chainsaws, Byte[] image) {
        super(id);
        this.name = name;
        this.url = url;
        this.email = email;
        this.image = image;
        if(chainsaws != null)
            this.chainsaws = chainsaws;
    }

    private String name;

    private String url;

    private String email;

    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private Set<Chainsaw> chainsaws = new HashSet<>();
}
