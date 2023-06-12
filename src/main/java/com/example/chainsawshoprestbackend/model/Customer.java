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

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity{

    @Builder
    public Customer(Long id, String username, String firstName, String lastName, String email, String password,
                    String phone, Set<Order> orders) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        if(orders != null)
            this.orders = orders;
    }


    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();
}
