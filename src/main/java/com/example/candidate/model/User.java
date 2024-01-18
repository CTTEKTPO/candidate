package com.example.candidate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    //номер записи
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //данные сотрудника
    @Column(name = "employeeName", columnDefinition = "text")
    private String employeeName;

    //логин сотрудника
    @Column(name = "username", columnDefinition = "text", nullable = false)
    private String username;

    //пароль сотрудника
    @Column(name = "password", columnDefinition = "text")
    private String password;

    //возможность входа
    @Column(name = "enabled", columnDefinition = "boolean")
    private Boolean enabled;

    /*доступ сотрудника
    limited - ограниченный
    full - полный*/
    @Column(name = "authority", columnDefinition = "text")
    private String authority;
}
