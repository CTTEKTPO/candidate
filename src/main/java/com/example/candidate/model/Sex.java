package com.example.candidate.model;


import jakarta.persistence.*;

@Entity
@Table(name = "sex")
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Sex(Long id, String gender) {
        this.id = id;
        this.gender = gender;
    }

    public Sex() {
    }

    @Column(columnDefinition = "TEXT", name = "sex_gender")
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

