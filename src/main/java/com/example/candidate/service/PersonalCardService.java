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
        Optional<PersonalCard> optionalCity = repo.findById(id);
        return optionalCity.orElse(null);
    }

    public boolean saveOrUpdate(PersonalCard item) {
        PersonalCard updated = repo.save(item);

        return repo.findById(updated.getId()).isPresent();
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
