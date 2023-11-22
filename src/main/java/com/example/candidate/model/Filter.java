package com.example.candidate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "filter")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", name = "filter_field")
    private String field;
    @Column(columnDefinition = "TEXT", name = "filter_condition")
    private String condition;
    @Column(columnDefinition = "TEXT", name = "filter_value")
    private String value;

    public Filter() {
    }

    public Filter(Long id, String field, String condition, String value) {
        this.id = id;
        this.field = field;
        this.condition = condition;
        this.value = value;
    }

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
