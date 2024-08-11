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
    public void setId(Long id) { this.id = id; }

    public RoleEnum getName() { return this.name; }
    public void setName(RoleEnum name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    //Getters & Setters
}
