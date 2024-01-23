package com.example.candidate.service;

import com.example.candidate.model.City;
import com.example.candidate.model.User;
import com.example.candidate.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class UserService {

    private static final Logger access_logs = Logger.getLogger("access_logs");
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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

    public void saveOrUpdate(User user, boolean bool) {
        if (bool) access_logs.info("Изменен уровень доступа пользователя " + user.getUsername() + " на " + user.getAuthority());
        if (user.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repo.save(user);
        } else {
            Optional<User> optionalUser = repo.findById(user.getId());
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                existingUser.setAuthority(user.getAuthority());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                existingUser.setEmployeeName(user.getEmployeeName());
                existingUser.setEnabled(user.getEnabled());
                repo.save(existingUser);
            }
        }
    }
    public boolean activatedUser(User user) {
            Optional<User> optionalUser = repo.findById(user.getId());
            User existingUser = optionalUser.get();
            if (!user.getEnabled() && "full".equals(existingUser.getAuthority()) && repo.countUsersByAuthority("full") == 1) {
                // Если это так, выводим ошибку
                //throw new IllegalStateException("Нельзя отключить единственного пользователя с полными правами.");
                return false;
            }
            existingUser.setEnabled(user.getEnabled());
            repo.save(existingUser);
            return true;
    }

    public boolean deleteById(Long id) {
        Optional<User> userToDelete = repo.findById(id);

        if (userToDelete.isPresent()) {
            User user = userToDelete.get();

            // Проверяем, является ли удаляемый пользователь единственным пользователем с правами 'full'
            if ("full".equals(user.getAuthority()) && repo.countUsersByAuthority("full") == 1) {
                // Если это так, не удаляем пользователя и возвращаем false
                return false;
            }

            repo.deleteById(id);
            return true;
        }

        return false;
    }
}
