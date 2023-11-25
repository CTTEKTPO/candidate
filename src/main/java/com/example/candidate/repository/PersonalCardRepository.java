package com.example.candidate.repository;

import com.example.candidate.model.PersonalCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PersonalCardRepository extends JpaRepository<PersonalCard, Long> {
    List<PersonalCard> findByFullNameContaining(String fullName);

    List<PersonalCard> findByDateOfBirth(Date dateOfBirth);

    List<PersonalCard> findByAge(Integer age);

    List<PersonalCard> findBySex(String sex);

    List<PersonalCard> findByCity_Name(String cityName);

    List<PersonalCard> findByPhone(String phone);

    List<PersonalCard> findByJobTitle_Title(String jobTitle);

    List<PersonalCard> findBySalary(Integer salary);

    List<PersonalCard> findByExperience(String experience);

    List<PersonalCard> findByEducation(String education);

    List<PersonalCard> findBySkills(String skills);

    List<PersonalCard> findByComments(String comments);

    List<PersonalCard> findByStatus_Field(String status);

    List<PersonalCard> findByCreationDate(String creationDate);

}
