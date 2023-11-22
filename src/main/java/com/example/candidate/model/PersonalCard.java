package com.example.candidate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "personal_card")
public class PersonalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public PersonalCard(Long id, String fullName,
                        Date dateOfBirth, Integer age,
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

    @Column(columnDefinition = "TEXT", name = "personal_card_full_name")
    private String fullName;

    @Column(columnDefinition = "date", name = "personal_card_DOB")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(columnDefinition = "Integer", name = "personal_card_age")
    private Integer age;

    @Column(columnDefinition = "TEXT", name = "personal_card_sex")
    private String sex;

    @Column(columnDefinition = "Integer", name = "personal_card_salary")
    private Integer salary;

    @Column(columnDefinition = "TEXT",name = "personal_card_phone")
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private String creationDate;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

//    public byte[] getImagesBytes() {
//        return imagesBytes;
//    }
//
//    public void setImagesBytes(byte[] imagesBytes) {
//        this.imagesBytes = imagesBytes;
//    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        try {
            if (creationDate != null && !creationDate.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(creationDate);
                this.creationDate = dateFormat.format(parsedDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
