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

    public void saveOrUpdate(PersonalCard item) {
        repo.save(item);

        //repo.findById(updated.getId());
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
