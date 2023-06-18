package com.example.candidate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Status(Long id, String field) {
        this.id = id;
        this.field = field;
    }

    public Status() {
    }

    @Column(columnDefinition = "TEXT", name = "status_field")
    private String field;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
