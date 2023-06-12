package com.example.chainsawshoprestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity{

    @Builder
    public User(Long id, String username, String firstName, String lastName, String email, String password, String phone) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;
}
