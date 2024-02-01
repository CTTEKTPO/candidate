package com.example.candidate.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "personal_card")
@Data
public class PersonalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public PersonalCard(Long id, String fullName,
                        LocalDate dateOfBirth, Integer age,
                        Integer salary, String phone,
                        String experience, String education,
                        String skills, String comments,
                        String sex, City city,
                        JobTitle jobTitle, Status status
//                        byte[] imagesBytes
    )
    {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.salary = salary;
        this.phone = phone;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.comments = comments;
        this.sex = sex;
        this.city = city;
        this.jobTitle = jobTitle;
        this.status = status;
//        this.imagesBytes = imagesBytes;
    }

    public PersonalCard() {
    }

    @Column(name = "personal_card_full_name", length = 128)
    private String fullName;

    @Column(columnDefinition = "date", name = "personal_card_DOB")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @Column(name = "personal_card_sex", length = 10)
    private String sex;

    @Column(columnDefinition = "Integer", name = "personal_card_salary")
    private Integer salary;

    @Column(name = "personal_card_phone", length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT",name = "personal_card_exp")
    private String experience;

    @Column(columnDefinition = "TEXT", name = "personal_card_edu")
    private String education;

    @Column(columnDefinition = "TEXT", name = "personal_card_skills")
    private String skills;

    @Column(columnDefinition = "TEXT", name = "personal_card_comments")
    private String comments;

//    @Column(columnDefinition = "bytea", name = "personal_card_image")
//    @Lob // аннотация указывает что это поле должно быть храниться в виде большого объекта
//    private byte[] imagesBytes;

    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "personal_card_creation_date", columnDefinition = "date")
    private LocalDate creationDate;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    public Integer getAge() {
        if (this.dateOfBirth != null) {
            return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        } else {
            return null; // или любое другое значение по умолчанию
        }
    }
//    public void setCreationDate(String creationDate) {
//        try {
//            if (creationDate != null && !creationDate.isEmpty()) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date parsedDate = dateFormat.parse(creationDate);
//                this.creationDate = dateFormat.format(parsedDate);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
