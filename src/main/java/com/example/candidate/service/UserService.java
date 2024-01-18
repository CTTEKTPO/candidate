package com.example.candidate.service;

import com.example.candidate.model.City;
import com.example.candidate.model.User;
import com.example.candidate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final
    UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll(){
        return repo.findAll();
    }

    public User getById(Long id) {
        Optional<User> optionalUser = repo.findById(id);
        return optionalUser.orElse(null);
    }

    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            repo.save(user);
        } else {
            Optional<User> optionalUser = repo.findById(user.getId());
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                existingUser.setAuthority(user.getAuthority());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setEmployeeName(user.getEmployeeName());
                repo.save(user);
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
