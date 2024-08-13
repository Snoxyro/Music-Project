package com.anyGroup.Music_Test.entities;

import jakarta.persistence.*;

@Table(name = "roles")
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(name = "description", nullable = false)
    private String description;

    //Getters & Setters
    public Long getId() { return this.id; }
    public RoleEntity setId(Long id) { this.id = id; return this; }

    public RoleEnum getName() { return this.name; }
    public RoleEntity setName(RoleEnum name) { this.name = name; return this; }

    public String getDescription() { return this.description; }
    public RoleEntity setDescription(String description) { this.description = description; return this; }
    //Getters & Setters
}
