package com.example.candidate.service;


import com.example.candidate.model.PersonalCard;
import com.example.candidate.repository.PersonalCardRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalCardService {

    final PersonalCardRepository repo;

    public PersonalCardService(PersonalCardRepository repo) {
        this.repo = repo;
    }

    public List<PersonalCard> getAll(){
        return repo.findAll();
    }

    public PersonalCard getById(Long id) {
        Optional<PersonalCard> result = repo.findById(id);
        return result.orElse(null);
    }

    public void saveOrUpdate(PersonalCard personalCard) {
        if (personalCard.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            repo.save(personalCard);
        } else {
            Optional<PersonalCard> optionalPersonalCard = repo.findById(personalCard.getId());
            if (optionalPersonalCard.isPresent()) {
                PersonalCard existingPersonalCard = optionalPersonalCard.get();
                existingPersonalCard.setFullName(personalCard.getFullName());
                existingPersonalCard.setDateOfBirth(personalCard.getDateOfBirth());
                existingPersonalCard.setAge(personalCard.getAge());
                existingPersonalCard.setSalary(personalCard.getSalary());
                existingPersonalCard.setPhone(personalCard.getPhone());
                existingPersonalCard.setExperience(personalCard.getExperience());
                existingPersonalCard.setEducation(personalCard.getEducation());
                existingPersonalCard.setSkills(personalCard.getSkills());
                existingPersonalCard.setComments(personalCard.getComments());
                existingPersonalCard.setSex(personalCard.getSex());
                existingPersonalCard.setCity(personalCard.getCity());
                existingPersonalCard.setJobTitle(personalCard.getJobTitle());
                existingPersonalCard.setStatus(personalCard.getStatus());
                existingPersonalCard.setCreationDate(existingPersonalCard.getCreationDate());
                repo.save(existingPersonalCard);
            }
        }
    }

    public boolean deleteById(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            // needless
        }
        return repo.findById(id).isEmpty();
    }

    public List<PersonalCard> getByJobTitle(String jobTitle) {
        return repo.findByJobTitle_Title(jobTitle);
    }

    public List<PersonalCard> getBySalary(Integer salary) {
        return repo.findBySalary(salary);
    }
    public List<PersonalCard> getByFullName(String fullName) {
        return repo.findByFullNameContaining(fullName);
    }

    public List<PersonalCard> getByDateOfBirth(String dateOfBirth) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
            return repo.findByDateOfBirth(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<PersonalCard> getByAge(Integer age) {
        return repo.findByAge(age);
    }

    public List<PersonalCard> getBySex(String sex) {
        return repo.findBySex(sex);
    }

    public List<PersonalCard> getByCity(String cityName) {
        return repo.findByCity_Name(cityName);
    }

    public List<PersonalCard> getByPhone(String phone) {
        return repo.findByPhone(phone);
    }

    public List<PersonalCard> getByExperience(String experience) {
        return repo.findByExperience(experience);
    }

    public List<PersonalCard> getByEducation(String education) {
        return repo.findByEducation(education);
    }

    public List<PersonalCard> getBySkills(String skills) {
        return repo.findBySkills(skills);
    }

    public List<PersonalCard> getByComments(String comments) {
        return repo.findByComments(comments);
    }

    public List<PersonalCard> getByStatus(String status) {
        return repo.findByStatus_Field(status);
    }

    public List<PersonalCard> getByCreationDate(String creationDate) {
        return repo.findByCreationDate(creationDate);
    }
}
