package com.example.candidate.model;

import jakarta.persistence.*;


@Entity
@Table(name = "job_title")
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public JobTitle(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public JobTitle() {
    }

    @Column(name = "job_title", length = 64)
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
