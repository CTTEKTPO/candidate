package com.example.candidate.service;

import com.example.candidate.model.PersonalCard;
import com.example.candidate.model.Sex;
import com.example.candidate.repository.PersonalCardRepository;
import com.example.candidate.repository.SexRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SexService {
    final SexRepository repo;

    public SexService(SexRepository repo) {
        this.repo = repo;
    }

    public List<Sex> getAll(){
        return repo.findAll();
    }

    public Sex getById(Long id) {
        Optional<Sex> optionalCity = repo.findById(id);
        return optionalCity.orElse(null);
    }

    public boolean saveOrUpdate(Sex item) {
        Sex updated = repo.save(item);

        return repo.findById(updated.getId()).isPresent();
    }
}
