package com.example.candidate.service;


import com.example.candidate.model.JobTitle;
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
        Optional<Status> optionalStatus = repo.findById(id);
        return optionalStatus.orElse(null);
    }

    public void saveOrUpdate(Status status) {
        if (status.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            repo.save(status);
        } else {
            Optional<Status> optionalStatus = repo.findById(status.getId());
            if (optionalStatus.isPresent()) {
                Status existingStatus = optionalStatus.get();
                existingStatus.setField(status.getField());
                repo.save(status);
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
