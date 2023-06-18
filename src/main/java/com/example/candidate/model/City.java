package com.example.candidate.model;

import jakarta.persistence.*;


@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City() {
    }

    @Column(columnDefinition = "TEXT",name = "city_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

