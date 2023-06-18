package com.example.candidate.service;


import com.example.candidate.model.Status;
import com.example.candidate.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    final StatusRepository repo;

    public StatusService(StatusRepository repo) {
        this.repo = repo;
    }

    public List<Status> getAll(){
        return repo.findAll();
    }

    public Status getById(Long id) {
        Optional<Status> optionalCity = repo.findById(id);
        return optionalCity.orElse(null);
    }

    public boolean saveOrUpdate(Status item) {
        Status updated = repo.save(item);

        return repo.findById(updated.getId()).isPresent();
    }

}
