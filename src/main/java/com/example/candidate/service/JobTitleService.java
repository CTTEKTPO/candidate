package com.example.candidate.service;

import com.example.candidate.model.City;
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
        Optional<JobTitle> optionalJobTitle = repo.findById(id);
        return optionalJobTitle.orElse(null);
    }

    public void saveOrUpdate(JobTitle jobTitle) {
        if (jobTitle.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            repo.save(jobTitle);
        } else {
            Optional<JobTitle> optionalJobTitle = repo.findById(jobTitle.getId());
            if (optionalJobTitle.isPresent()) {
                JobTitle existingJobTitle = optionalJobTitle.get();
                existingJobTitle.setTitle(jobTitle.getTitle());
                repo.save(existingJobTitle);
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
