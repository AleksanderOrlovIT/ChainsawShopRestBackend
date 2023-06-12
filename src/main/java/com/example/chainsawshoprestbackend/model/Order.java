package com.example.chainsawshoprestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private LocalDate date;

    @ManyToOne
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "order_chainsaws", joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "chainsaw_id"))
    private Set<Chainsaw> chainsaws = new HashSet<>();

    @ElementCollection
    private Map<String, Integer> chainsawQuantities;
}
