package com.example.chainsawshoprestbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Builder
    public Order(Long id, LocalDate date, Customer customer, List<Chainsaw> chainsaws, Map<Long, Integer> chainsawQuantities) {
        super(id);
        this.date = date;
        this.customer = customer;
        if(chainsaws != null)
            this.chainsaws = chainsaws;
        if(chainsawQuantities != null)
            this.chainsawQuantities = chainsawQuantities;
    }

    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "order_chainsaws", joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "chainsaw_id"))
    private List<Chainsaw> chainsaws = new ArrayList<>();

    @ElementCollection
    private Map<Long, Integer> chainsawQuantities = new HashMap<>();
}
