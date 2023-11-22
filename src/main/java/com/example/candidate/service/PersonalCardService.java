package com.example.candidate.service;


import com.example.candidate.model.PersonalCard;
import com.example.candidate.repository.PersonalCardRepository;
import org.springframework.stereotype.Service;

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
                repo.save(personalCard);
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
}
