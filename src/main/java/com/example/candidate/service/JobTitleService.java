package com.example.candidate.service;

import com.example.candidate.model.JobTitle;
import com.example.candidate.repository.JobTitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobTitleService {

    final JobTitleRepository repo;

    public JobTitleService(JobTitleRepository repo) {
        this.repo = repo;
    }

    public List<JobTitle> getAll(){
        return repo.findAll();
    }

    public JobTitle getById(Long id) {
        Optional<JobTitle> optionalCity = repo.findById(id);
        return optionalCity.orElse(null);
    }

    public boolean saveOrUpdate(JobTitle item) {
        JobTitle updated = repo.save(item);

        return repo.findById(updated.getId()).isPresent();
    }
}
