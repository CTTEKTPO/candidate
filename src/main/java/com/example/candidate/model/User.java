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
    @Column(name = "employeeName", length = 128)
    private String employeeName;

    //логин сотрудника
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    //пароль сотрудника
    @Column(name = "password", length = 256)
    private String password;

    //возможность входа
    @Column(name = "enabled", columnDefinition = "boolean")
    private Boolean enabled;

    /*доступ сотрудника
    limited - ограниченный
    full - полный*/
    @Column(name = "authority", length = 15)
    private String authority;
}
