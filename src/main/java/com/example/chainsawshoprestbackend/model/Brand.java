package com.example.chainsawshoprestbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Brand extends BaseEntity{

    @Builder
    public Brand(Long id, String name, String url, String email, List<Chainsaw> chainsaws, Byte[] image) {
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<Chainsaw> chainsaws = new ArrayList<>();
}
